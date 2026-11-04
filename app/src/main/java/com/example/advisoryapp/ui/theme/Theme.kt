package com.example.advisoryapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF38BDF8), // Vibrant Light Sky Blue
    onPrimary = Color(0xFF0F172A),
    secondary = Color(0xFF2DD4BF), // Bright Teal
    onSecondary = Color(0xFF0F172A),
    tertiary = Color(0xFFFBBF24), // Warm Gold
    background = Color(0xFF0F172A), // Deep Slate Navy
    surface = Color(0xFF1E293B), // Card Surface Navy
    onBackground = Color(0xFFF8FAFC), // Crisp White Text
    onSurface = Color(0xFFF8FAFC), // Crisp White Text
    surfaceVariant = Color(0xFF334155), // Secondary Container
    onSurfaceVariant = Color(0xFFCBD5E1), // Subtitle Slate Text
    outline = Color(0xFF475569) // Border Stroke
)

@Composable
fun AdvisoryAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
