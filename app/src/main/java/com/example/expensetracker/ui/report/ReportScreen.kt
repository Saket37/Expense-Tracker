package com.example.expensetracker.ui.report

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.designsystem.component.AppScaffold
import com.example.expensetracker.designsystem.component.BarGraphItem
import com.example.expensetracker.designsystem.component.ExpenseCategoryItem
import com.example.expensetracker.designsystem.theme.ExpenseTrackerTheme
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.utils.GeneratePdf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ReportScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ReportScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    ReportScreen(
        navController = navController,
        uiState = uiState
    )
}

@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: ExpenseReportState
) {
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    val formattedExpense = formatter.format(uiState.grandTotal)
    AppScaffold(onThemeIconClick = {
    }, navController = navController, showAppBar = false) {
        val listState = rememberLazyListState()
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Last 7 days Report",
                style = LocalTypography.current.headingLarge,
                fontSize = 32.sp
            )

            Text(
                "Your Total expense : $formattedExpense",
                style = LocalTypography.current.headingSmall,
                fontSize = 18.sp
            )

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 32.dp
                ),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    Text(
                        "Daily Total Spent",
                        style = LocalTypography.current.headingSmall,
                        fontSize = 16.sp
                    )
                }
                item {
                    BarGraphItem(
                        barDetails = uiState.barDetails,
                    )
                }

                item {
                    Text(
                        "Category-wise Total",
                        style = LocalTypography.current.headingSmall,
                        fontSize = 16.sp
                    )
                }

                itemsIndexed(items = uiState.categoryTotals) { index, item ->
                    ExpenseCategoryItem(
                        title = item.category.displayName,
                        expense = item.total
                    )
                }

                item {
                    Spacer(Modifier.height(36.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
//                                val bitmap = createBitmapFromComposable(context, 1000, 500) {
//                                    ExpenseTrackerTheme {
//                                        BarGraphItem(uiState.barDetails)
//                                    }
//                                }
                                val (file, uri) = GeneratePdf.generatePdf(
                                    context,
                                    expensesList = uiState.expensesByDay,
                                    totalAmount = uiState.grandTotal,
                                    categoryTotal = uiState.categoryTotals
                                )
                                launch(Dispatchers.Main) {
                                    if (file != null || uri != null) {
                                        Toast.makeText(
                                            context,
                                            "Report saved to Downloads",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        openPdf(context, file, uri)
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Failed to generate report",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "Export data to PDF", style = LocalTypography.current.label, fontSize = 16.sp)
                    }
                }


            }
        }


    }

}

private fun openPdf(context: Context, file: File?, uri: Uri?) {
    val pdfUri: Uri? = if (file != null) {
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } else {
        uri
    }

    if (pdfUri == null) {
        Toast.makeText(context, "Could not get PDF Uri", Toast.LENGTH_SHORT).show()
        return
    }

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(pdfUri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No app found to open PDF files.", Toast.LENGTH_LONG).show()
    }
}
