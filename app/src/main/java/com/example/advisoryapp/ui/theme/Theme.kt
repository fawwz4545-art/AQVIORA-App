package com.example.advisoryapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ExecutiveNavy, // Color(0xFF0B132B)
    onPrimary = Color.White,
    secondary = AccentTeal, // Color(0xFF00B4D8)
    onSecondary = Color.White,
    tertiary = AmberGold, // Color(0xFFE9C46A)
    background = BackgroundLight, // Color(0xFFF8FAFC)
    surface = CardWhite, // Color(0xFFFFFFFF)
    onBackground = TextPrimary, // Color(0xFF0F172A)
    onSurface = TextPrimary, // Color(0xFF0F172A)
    surfaceVariant = Color(0xFFEAEFF2),
    onSurfaceVariant = TextSecondary, // Color(0xFF475569)
    outline = BorderColor // Color(0xFFCBD5E1)
)

@Composable
fun AdvisoryAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
