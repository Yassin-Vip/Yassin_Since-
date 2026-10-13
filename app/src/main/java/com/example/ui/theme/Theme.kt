package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryIndigoLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryIndigoDark,
    onPrimaryContainer = Color(0xFFE0E7FF),
    secondary = SecondaryVioletLight,
    onSecondary = Color.White,
    secondaryContainer = SecondaryVioletDark,
    onSecondaryContainer = Color(0xFFEDE9FE),
    tertiary = TertiaryCyanLight,
    onTertiary = Color.Black,
    tertiaryContainer = TertiaryCyanDark,
    onTertiaryContainer = Color(0xFFCFFAFE),
    background = DarkBg,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkSurfaceBorder,
    error = AccentRose,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryIndigo,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEEF2FF),
    onPrimaryContainer = PrimaryIndigoDark,
    secondary = SecondaryViolet,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF5F3FF),
    onSecondaryContainer = SecondaryVioletDark,
    tertiary = TertiaryCyanDark,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFECFEFF),
    onTertiaryContainer = Color(0xFF164E63),
    background = LightBg,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceElevated,
    onSurfaceVariant = LightTextSecondary,
    outline = LightSurfaceBorder,
    error = AccentRose,
    onError = Color.White
)

@Composable
fun YassinStudyTheme(
    darkTheme: Boolean = true, // Default to ultra-stylish modern dark mode requested by user
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
