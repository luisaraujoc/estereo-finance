package com.coutinho.estereofinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coutinho.estereofinance.ui.screens.auth.LoginScreen
import com.coutinho.estereofinance.ui.screens.auth.RegisterScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // Auth Screens
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Home.route) },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        // Adicione outras telas principais aqui conforme necessário
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    // Adicione outras telas aqui
    // object Transactions : Screen("transactions")
    // object Categories : Screen("categories")
    // object Reports : Screen("reports")
}