package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = MetraCyan,
    onPrimary = DarkObsidian,
    primaryContainer = Color(0xFF0F172A),
    onPrimaryContainer = MetraCyan,
    secondary = MetraIndigo,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF1E293B),
    onSecondaryContainer = MetraIndigo,
    tertiary = MetraEmerald,
    onTertiary = DarkObsidian,
    tertiaryContainer = Color(0xFF064E3B),
    onTertiaryContainer = MetraEmerald,
    background = DarkNavyBg,
    onBackground = DarkTextPrimary,
    surface = DarkCardSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder,
    error = MetraRose,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = MetraIndigo,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = Color(0xFF0369A1),
    secondary = MetraBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF0F9FF),
    onSecondaryContainer = Color(0xFF0284C7),
    tertiary = MetraCyan,
    onTertiary = DarkObsidian,
    background = LightBg,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder,
    error = MetraRose,
    onError = Color.White
)

@Composable
fun MetraTheme(
    darkTheme: Boolean = false, // Changed default to False (Light Mode)
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
