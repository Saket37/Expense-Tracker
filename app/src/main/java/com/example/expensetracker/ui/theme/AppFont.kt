package com.example.expensetracker.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.expensetracker.R

val AppFonts = FontFamily(
    Font(R.font.urbanist_bold, FontWeight.Bold),
    Font(R.font.urbanist_semibold, FontWeight.SemiBold),
    Font(R.font.urbanist_medium, FontWeight.Medium),
    Font(R.font.urbanist_regular, FontWeight.Normal)
)

val ExpenseTrackerTypography = AppTypography(
    headingLarge = TextStyle(fontFamily = AppFonts, fontWeight = FontWeight.Bold),
    headingSmall = TextStyle(fontFamily = AppFonts, fontWeight = FontWeight.SemiBold),
    label = TextStyle(fontFamily = AppFonts, fontWeight = FontWeight.Medium),
    body = TextStyle(fontFamily = AppFonts, fontWeight = FontWeight.Normal)
)