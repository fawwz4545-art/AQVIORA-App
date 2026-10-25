package com.example.advisoryapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advisoryapp.model.PillarType

@Composable
fun PillarCard(
    pillar: PillarType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    val (icon, iconBg, iconColor) = when (pillar) {
        PillarType.ACCOUNTING -> Triple(Icons.Default.AccountBalance, if (isDark) Color(0xFF1E3A8A) else Color(0xFFE6F0FA), if (isDark) Color(0xFF93C5FD) else Color(0xFF1D4ED8))
        PillarType.TAX -> Triple(Icons.AutoMirrored.Filled.ReceiptLong, if (isDark) Color(0xFF78350F) else Color(0xFFFEF3C7), if (isDark) Color(0xFFFDE68A) else Color(0xFFD97706))
        PillarType.FINANCE -> Triple(Icons.Default.Analytics, if (isDark) Color(0xFF064E3B) else Color(0xFFE9F5F3), if (isDark) Color(0xFF6EE7B7) else Color(0xFF2A9D8F))
        PillarType.COSTING -> Triple(Icons.Default.Calculate, if (isDark) Color(0xFF164E63) else Color(0xFFE0F7FA), if (isDark) Color(0xFF67E8F9) else Color(0xFF00B4D8))
        PillarType.BUDGETING -> Triple(Icons.Default.PieChart, if (isDark) Color(0xFF312E81) else Color(0xFFEEF2FF), if (isDark) Color(0xFFA5B4FC) else Color(0xFF4F46E5))
        PillarType.INTERNAL_CONTROL -> Triple(Icons.Default.Security, if (isDark) Color(0xFF881337) else Color(0xFFFDF6F2), if (isDark) Color(0xFFFECDD3) else Color(0xFFE76F51))
        PillarType.RISK_MANAGEMENT -> Triple(Icons.Default.Gavel, if (isDark) Color(0xFF581C87) else Color(0xFFF3E8FF), if (isDark) Color(0xFFE9D5FF) else Color(0xFF7C3AED))
        PillarType.INTERNAL_AUDIT -> Triple(Icons.Default.VerifiedUser, if (isDark) Color(0xFF075985) else Color(0xFFE0F2FE), if (isDark) Color(0xFF7DD3FC) else Color(0xFF0284C7))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = pillar.displayName,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pillar.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = pillar.description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Cakupan: ${pillar.exampleServices}",
                    fontSize = 11.sp,
                    color = iconColor,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
