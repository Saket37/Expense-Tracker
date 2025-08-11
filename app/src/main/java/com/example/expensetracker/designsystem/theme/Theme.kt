package com.example.expensetracker.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.expensetracker.domain.models.AppTheme


private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,           // #50BB77 - Main interactive color
    onPrimary = DarkGreen,            // #0C2718 - Text/icons on primary color
    secondary = LightGreen.copy(0.1f),       // #AAD7A4 - Secondary interactive color
    onSecondary = DarkGreen,          // #0C2718 - Text/icons on secondary color
    tertiary = LightGray,             // #515151 - For less important elements
    onTertiary = OffWhite,         // #FDFDFD - Text/icons on tertiary color
    background = DarkGreen,           // #0C2718 - Screen background
    onBackground = OffWhite,       // #FDFDFD - Main text color
    surface = DarkGray,               // #383838 - Card and component backgrounds
    onSurface = LightGreen,          // #FDFDFD - Text on cards/components
    onSurfaceVariant = DarkGreen,// #AAD7A4 - For subtitles or decorative icons,
    onSecondaryContainer = LightGray,
    outline = LightGreen,
    surfaceContainerHighest = Color.Black,
    surfaceContainerLowest = OffWhite,
    inverseSurface = PrimaryGreen
)

// Mapped colors for the Light Theme using the full palette
private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,           // #50BB77 - Main interactive color
    onPrimary = OffWhite,          // #FDFDFD - Text/icons on primary color
    secondary = PrimaryGreen,       // #AAD7A4 - Secondary interactive color
    onSecondary = DarkGreen,          // #0C2718 - Text/icons on secondary color
    tertiary = LightGreen,             // #0C2718 - For less important elements
    onTertiary = PrimaryGreen,         // #FDFDFD - Text/icons on tertiary color
    background = OffWhite,         // #FDFDFD - Screen background
    onBackground = DarkGreen,          // #383838 - Main text color
    surface = PrimaryGreen,            // #FDFDFD - Card and component backgrounds
    onSurface = LightGreen,             // #383838 - Text on cards/components
    onSurfaceVariant = LightGray,     // #515151 - For subtitles or disabled text
    outline = LightGray.copy(0.2f),
    onSecondaryContainer = OffWhite,
    surfaceContainerHighest = OffWhite,
    surfaceContainerLowest = Color.Black,
    inverseSurface = DarkGray

)


@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme = AppTheme.SYSTEM,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appTheme) {
        AppTheme.LIGHT -> LightColorScheme
        AppTheme.DARK -> DarkColorScheme
        AppTheme.SYSTEM -> {
//            if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                val context = LocalContext.current
//                if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
//                    context
//                )
//            } else {
            if (darkTheme) DarkColorScheme else LightColorScheme
            //}
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        // A SideEffect runs after every successful composition
        SideEffect {
            val window = (view.context as Activity).window

            // Set the status bar color to be transparent
            // This is often done with enableEdgeToEdge, but can be set here too
            //window.statusBarColor = colorScheme.background.toArgb()

            // This line tells the system whether the icons should be dark or light
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                when (appTheme) {
                    AppTheme.LIGHT -> true
                    AppTheme.DARK -> false
                    AppTheme.SYSTEM -> !darkTheme
                }
        }
    }

//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }


    CompositionLocalProvider(
        LocalTypography provides ExpenseTrackerTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
//    {
//        CompositionLocalProvider(LocalTypography provides ExpenseTrackerTypography) {
//            content
//        }
//    }
}