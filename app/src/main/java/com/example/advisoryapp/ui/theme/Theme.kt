package com.example.advisoryapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
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

private val LightColorScheme = lightColorScheme(
    primary = ExecutiveNavy,
    onPrimary = Color.White,
    secondary = AccentTeal,
    onSecondary = Color.White,
    tertiary = AmberGold,
    background = BackgroundLight,
    surface = CardWhite,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFEAEFF2),
    onSurfaceVariant = TextSecondary,
    outline = BorderColor
)

@Composable
fun AdvisoryAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to preserve high-contrast executive theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
