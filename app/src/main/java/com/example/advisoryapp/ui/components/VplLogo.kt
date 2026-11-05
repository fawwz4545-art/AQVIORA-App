package com.example.advisoryapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.advisoryapp.R

@Composable
fun VplLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.vpl_logo),
        contentDescription = "VPL Consulting Logo",
        modifier = modifier.size(100.dp)
    )
}
