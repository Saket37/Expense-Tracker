package com.example.expensetracker.designsystem.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.designsystem.theme.DarkGreen
import com.example.expensetracker.designsystem.theme.FoodLight
import com.example.expensetracker.designsystem.theme.LightGray
import com.example.expensetracker.designsystem.theme.LightGreen
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.designsystem.theme.PurpleGrey80
import com.example.expensetracker.designsystem.theme.Test
import com.example.expensetracker.ui.report.BarDetails


@Composable
fun BarGraphItem(
    barDetails: List<BarDetails>,
    barColor: Color = DarkGreen
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = LightGreen,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 12.dp)
    ) {
        val maxValue = (barDetails.maxOfOrNull { it.barValue } ?: 100f)

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                barDetails.forEach { detail ->
                    val targetHeight = if (maxValue != 0f) {
                        (detail.barValue / maxValue) * 100
                    } else {
                        0f
                    }

                    val animatedHeight by animateFloatAsState(
                        targetValue = targetHeight,
                        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        // TODO Amount text - change color
                        Text(
                            detail.barText,
                            style = LocalTypography.current.label,
                            color = barColor,
                            fontSize = 12.sp,
                        )
                        Spacer(Modifier.height(3.dp))
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .height(animatedHeight.dp)
                                .background(
                                    color = barColor,
                                    shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
                                )
                        )

                    }
                }
            }
        }
        HorizontalDivider(
            color = LightGray,
            thickness = 1.dp,
        )
        Spacer(Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            barDetails.forEachIndexed { index, barDetail ->
                Text(
                    barDetail.label,
                    style = LocalTypography.current.label,
                    fontSize = 10.sp,
                    color = LightGray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
