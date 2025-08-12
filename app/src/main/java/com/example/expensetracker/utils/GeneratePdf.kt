package com.example.expensetracker.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.graphics.scale
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.data.model.CategoryTotal
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object GeneratePdf {
    private const val PAGE_WIDTH = 595
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 40
    private const val TITLE_TEXT_SIZE = 20f
    private const val BODY_TEXT_SIZE = 14f
    private const val ROW_HEIGHT = 25f
    private const val SECTION_SPACING = 40f


    fun generatePdf(
        context: Context,
        expensesList: Map<Long, List<Expense>>,
        totalAmount: Double,
        categoryTotal: List<CategoryTotal>
    ): Pair<File?, Uri?> {
        val pdfDoc = PdfDocument()

        var currentPage: PdfDocument.Page? = null
        var canvas: Canvas? = null
        var yPosition = MARGIN.toFloat()

        fun startNewPage() {
            currentPage?.let { pdfDoc.finishPage(it) }

            val pageInfo =
                PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pdfDoc.pages.size + 1).create()
            currentPage = pdfDoc.startPage(pageInfo)
            canvas = currentPage!!.canvas

            yPosition = MARGIN.toFloat()
        }

        fun checkAndStartNewPageIfNeeded(neededHeight: Float) {
            if (yPosition + neededHeight > PAGE_HEIGHT - MARGIN) {
                startNewPage()
            }
        }

        startNewPage()

        val titlePaint = Paint().apply {
            textSize = TITLE_TEXT_SIZE
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
        }

        val textPaint = Paint().apply {
            textSize = BODY_TEXT_SIZE
            color = Color.BLACK
        }

        val tableHeaderPaint = Paint(textPaint).apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        val linePaint = Paint().apply {
            color = Color.GRAY
            strokeWidth = 1f
        }


        // Main Title
        checkAndStartNewPageIfNeeded(TITLE_TEXT_SIZE + SECTION_SPACING)
        canvas!!.drawText(
            "Past 7 Days Expense Report",
            (PAGE_WIDTH / 2).toFloat(),
            yPosition + TITLE_TEXT_SIZE,
            titlePaint
        )
        yPosition += TITLE_TEXT_SIZE + SECTION_SPACING

        // Total Expense
        checkAndStartNewPageIfNeeded(BODY_TEXT_SIZE + SECTION_SPACING)
        canvas!!.drawText(
            "Total Expense: ₹${"%.2f".format(totalAmount)}",
            MARGIN.toFloat(),
            yPosition,
            textPaint
        )
        yPosition += SECTION_SPACING

        // Expenses Table
        val columnWidths = listOf(150f, 135f, 100f, 130f)
        expensesList.toSortedMap(compareByDescending { it }).forEach { (date, expenses) ->
            val minBlockHeight = ROW_HEIGHT * 3
            checkAndStartNewPageIfNeeded(minBlockHeight)

            val dateString = Utility.formatDate(date)
            canvas!!.drawText(dateString, MARGIN.toFloat(), yPosition, tableHeaderPaint)
            yPosition += ROW_HEIGHT

            // table header row
            drawTableRow(
                canvas!!,
                yPosition,
                listOf("Title", "Category", "Amount", "Notes"),
                tableHeaderPaint,
                columnWidths
            )
            yPosition += 5
            canvas!!.drawLine(
                MARGIN.toFloat(),
                yPosition,
                (PAGE_WIDTH - MARGIN).toFloat(),
                yPosition,
                linePaint
            )
            yPosition += 30

            expenses.forEach { expense ->
                checkAndStartNewPageIfNeeded(ROW_HEIGHT)
                drawTableRow(
                    canvas!!, yPosition,
                    listOf(
                        expense.title,
                        expense.category.displayName,
                        "₹${expense.amount}",
                        expense.notes ?: ""
                    ),
                    textPaint, columnWidths
                )
                yPosition += ROW_HEIGHT
            }
            yPosition += ROW_HEIGHT
        }

        // Category Totals
        checkAndStartNewPageIfNeeded(TITLE_TEXT_SIZE + ROW_HEIGHT * 2)
        yPosition += SECTION_SPACING / 2
        canvas!!.drawText(
            "Category-wise Total Spends",
            MARGIN.toFloat(),
            yPosition,
            titlePaint.apply { textAlign = Paint.Align.LEFT })
        yPosition += ROW_HEIGHT + 10

        categoryTotal.forEach { catTotal ->
            checkAndStartNewPageIfNeeded(ROW_HEIGHT)
            canvas!!.drawText(
                "${catTotal.category.displayName}:",
                MARGIN.toFloat(),
                yPosition,
                tableHeaderPaint
            )
            canvas!!.drawText("₹${"%.2f".format(catTotal.total)}", 300f, yPosition, textPaint)
            yPosition += ROW_HEIGHT
        }

        // --- Finish and Save ---
        pdfDoc.finishPage(currentPage!!)

        return savePdf(context, pdfDoc)
    }

    private fun scaleBitmapToWidth(bitmap: Bitmap, width: Int): Bitmap {
        val ratio = bitmap.height.toFloat() / bitmap.width.toFloat()
        val height = (width * ratio).toInt()
        return bitmap.scale(width, height)
    }

    private fun drawTableRow(
        canvas: android.graphics.Canvas,
        y: Float,
        texts: List<String>,
        paint: Paint,
        widths: List<Float>
    ) {
        var x = MARGIN.toFloat()
        texts.forEachIndexed { index, text ->
            canvas.drawText(text, x, y, paint)
            x += widths[index]
        }
    }


    fun savePdf(context: Context, document: PdfDocument): Pair<File?, Uri?> {
        val fileName = "ExpenseReport_${System.currentTimeMillis()}.pdf"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Modern approach (Android 10+)
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if (uri != null) {
                try {
                    resolver.openOutputStream(uri)?.use { document.writeTo(it) }
                    return Pair(null, uri) // Return the Uri
                } catch (e: IOException) { e.printStackTrace() }
            }
        } else {
            // Legacy approach (Android 9 and below)
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)
            try {
                FileOutputStream(file).use { document.writeTo(it) }
                return Pair(file, null) // Return the File
            } catch (e: IOException) { e.printStackTrace() }
        }

        document.close()
        return Pair(null, null) // Return nulls on failure
    }}


