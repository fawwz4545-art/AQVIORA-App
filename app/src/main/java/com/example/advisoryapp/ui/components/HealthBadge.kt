package com.example.advisoryapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advisoryapp.model.HealthStatus

@Composable
fun HealthBadge(
    status: HealthStatus,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    val (bgColor, textColor, borderColor) = when (status) {
        HealthStatus.BASIC -> if (isDark) {
            Triple(Color(0xFF064E3B), Color(0xFF6EE7B7), Color(0xFF10B981))
        } else {
            Triple(Color(0xFFD1FAE5), Color(0xFF059669), Color(0xFF10B981).copy(alpha = 0.3f))
        }
        HealthStatus.IMPROVEMENT_REQUIRED -> if (isDark) {
            Triple(Color(0xFF78350F), Color(0xFFFDE68A), Color(0xFFF59E0B))
        } else {
            Triple(Color(0xFFFEF3C7), Color(0xFFD97706), Color(0xFFF59E0B).copy(alpha = 0.3f))
        }
        HealthStatus.HIGH_ATTENTION -> if (isDark) {
            Triple(Color(0xFF881337), Color(0xFFFECDD3), Color(0xFFF43F5E))
        } else {
            Triple(Color(0xFFFFE4E6), Color(0xFFE11D48), Color(0xFFF43F5E).copy(alpha = 0.3f))
        }
    }

    Box(
        modifier = modifier
            .background(color = bgColor, shape = CircleShape)
            .border(BorderStroke(1.dp, borderColor), shape = CircleShape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.sp
        )
    }
}
