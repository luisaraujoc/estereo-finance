package com.coutinho.estereofinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coutinho.estereofinance.ui.screens.auth.LoginScreen
import com.coutinho.estereofinance.ui.screens.auth.RegisterScreen
import com.coutinho.estereofinance.ui.screens.home.HomeScreen
import com.coutinho.estereofinance.ui.screens.transactions.TransactionsScreen

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
                onRegisterSuccess = { navController.navigate(Screen.Login.route) },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToTransactions = { navController.navigate("transactions") } // Placeholder for transactions screen
            )
        }

        composable(Screen.Transactions.route) {
            TransactionsScreen()
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Transactions : Screen("transactions")
}