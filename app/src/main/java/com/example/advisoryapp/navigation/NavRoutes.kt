package com.example.advisoryapp.navigation

sealed class Screen(val route: String, val label: String) {
    object Home : Screen(route = "home", label = "Beranda")
    object HealthCheck : Screen(route = "health_check", label = "Diagnostic")
    object Experts : Screen(route = "experts", label = "Experts")
    object Dashboard : Screen(route = "dashboard", label = "Dashboard")
    object Login : Screen(route = "login", label = "Login")
    object Register : Screen(route = "register", label = "Register")
}
