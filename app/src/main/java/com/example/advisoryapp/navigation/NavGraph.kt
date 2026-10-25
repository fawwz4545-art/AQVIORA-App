package com.example.advisoryapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.advisoryapp.ui.AdvisoryViewModel
import com.example.advisoryapp.ui.components.AdvisoryBottomNavBar
import com.example.advisoryapp.ui.screens.DashboardScreen
import com.example.advisoryapp.ui.screens.DiagnosticScreen
import com.example.advisoryapp.ui.screens.ExpertsScreen
import com.example.advisoryapp.ui.screens.HomeScreen
import com.example.advisoryapp.ui.screens.LoginScreen
import com.example.advisoryapp.ui.screens.RegisterScreen

@Composable
fun AdvisoryNavGraph(
    viewModel: AdvisoryViewModel,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val isAuthScreen = currentRoute == Screen.Login.route || currentRoute == Screen.Register.route

    Scaffold(
        bottomBar = {
            if (!isAuthScreen) {
                AdvisoryBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToDiagnostic = { navController.navigate(Screen.HealthCheck.route) },
                    onNavigateToExperts = { navController.navigate(Screen.Experts.route) }
                )
            }
            composable(Screen.HealthCheck.route) {
                DiagnosticScreen(
                    viewModel = viewModel,
                    onNavigateToExperts = { navController.navigate(Screen.Experts.route) }
                )
            }
            composable(Screen.Experts.route) {
                ExpertsScreen(viewModel = viewModel)
            }
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToLogin = { navController.navigate(Screen.Login.route) }
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    viewModel = viewModel,
                    onRegisterSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Home.route)
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
        }
    }
}
