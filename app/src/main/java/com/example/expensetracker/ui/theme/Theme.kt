package com.example.expensetracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.expensetracker.domain.models.AppTheme
import com.example.expensetracker.domain.repository.AppThemeRepository
import org.koin.compose.koinInject


private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,           // #50BB77 - Main interactive color
    onPrimary = DarkGreen,            // #0C2718 - Text/icons on primary color
    secondary = LightGreen,       // #AAD7A4 - Secondary interactive color
    onSecondary = DarkGreen,          // #0C2718 - Text/icons on secondary color
    tertiary = LightGray,             // #515151 - For less important elements
    onTertiary = LightGray,         // #FDFDFD - Text/icons on tertiary color
    background = DarkGreen,           // #0C2718 - Screen background
    onBackground = OffWhite,       // #FDFDFD - Main text color
    surface = DarkGray,               // #383838 - Card and component backgrounds
    onSurface = LightGreen,          // #FDFDFD - Text on cards/components
    onSurfaceVariant = DarkGreen,// #AAD7A4 - For subtitles or decorative icons
)

// Mapped colors for the Light Theme using the full palette
private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,           // #50BB77 - Main interactive color
    onPrimary = OffWhite,          // #FDFDFD - Text/icons on primary color
    secondary = LightGreen,       // #AAD7A4 - Secondary interactive color
    onSecondary = DarkGreen,          // #0C2718 - Text/icons on secondary color
    tertiary = DarkGreen,             // #0C2718 - For less important elements
    onTertiary = LightGray,         // #FDFDFD - Text/icons on tertiary color
    background = OffWhite,         // #FDFDFD - Screen background
    onBackground = DarkGreen,          // #383838 - Main text color
    surface = OffWhite,            // #FDFDFD - Card and component backgrounds
    onSurface = LightGreen,             // #383838 - Text on cards/components
    onSurfaceVariant = LightGray,     // #515151 - For subtitles or disabled text
)


@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val appThemeRepository: AppThemeRepository = koinInject()
    val theme by appThemeRepository.theme.collectAsState(initial = AppTheme.SYSTEM)

    val colorScheme = when (theme) {
        AppTheme.LIGHT -> LightColorScheme
        AppTheme.DARK -> DarkColorScheme
        AppTheme.SYSTEM -> {
            if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val context = LocalContext.current
                if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                    context
                )
            } else {
                if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        CompositionLocalProvider(LocalTypography provides ExpenseTrackerTypography) {
            content
        }
    }
}