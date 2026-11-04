package com.example.advisoryapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.advisoryapp.R

@Composable
fun VplLogo(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "VPL Consulting Logo",
        tint = Color.Unspecified,
        modifier = modifier.size(54.dp)
    )
}
