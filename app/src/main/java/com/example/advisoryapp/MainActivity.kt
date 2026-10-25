package com.example.advisoryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.advisoryapp.navigation.AdvisoryNavGraph
import com.example.advisoryapp.ui.AdvisoryViewModel
import com.example.advisoryapp.ui.theme.AdvisoryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdvisoryAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val advisoryViewModel: AdvisoryViewModel = viewModel()
                    AdvisoryNavGraph(viewModel = advisoryViewModel)
                }
            }
        }
    }
}
