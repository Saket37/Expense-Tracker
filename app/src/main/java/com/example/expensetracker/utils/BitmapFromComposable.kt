package com.example.expensetracker.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.compositionContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Job
//
//suspend fun createBitmapFromComposable(
//    context: Context,
//    width: Int,
//    height: Int,
//    composable: @Composable () -> Unit
//): Bitmap {
//    val composeView = ComposeView(context)
//
//    // THE ONLY CHANGE IS ON THIS LINE:
//    val recomposer = Recomposer(AndroidUiDispatcher.Main) // Use .Main instead of .CurrentThread
//
//    composeView.compositionContext = recomposer
//
//    try {
//        // The 'run' function takes a suspend block where composition is active.
//        return recomposer.run {
//            composeView.setContent(composable)
//            recomposer.awaitIdle()
//
//            composeView.measure(
//                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
//            )
//            composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)
//
//            val bitmap = Bitmap.createBitmap(
//                composeView.measuredWidth,
//                composeView.measuredHeight,
//                Bitmap.Config.ARGB_8888
//            )
//            val canvas = android.graphics.Canvas(bitmap)
//            composeView.draw(canvas)
//            bitmap
//        }
//    } finally {
//        composeView.disposeComposition()
//    }
//}