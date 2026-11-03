package com.example.advisoryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.advisoryapp.data.AdvisoryRepository
import com.example.advisoryapp.navigation.AdvisoryNavGraph
import com.example.advisoryapp.ui.AdvisoryViewModel
import com.example.advisoryapp.ui.theme.AdvisoryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AdvisoryRepository(applicationContext)
        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AdvisoryViewModel(repository) as T
            }
        }

        setContent {
            AdvisoryAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val advisoryViewModel: AdvisoryViewModel = viewModel(factory = viewModelFactory)
                    AdvisoryNavGraph(viewModel = advisoryViewModel)
                }
            }
        }
    }
}
