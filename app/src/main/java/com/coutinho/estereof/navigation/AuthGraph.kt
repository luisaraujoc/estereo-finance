// src/main/java/com/coutinho/estereof/navigation/AuthGraph.kt
package com.coutinho.estereof.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation // Importação necessária
import com.coutinho.estereof.ui.auth.LoginScreen
import com.coutinho.estereof.ui.auth.RegisterScreen

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Register : AuthScreen("register")
}

object AppDestinations {
    const val AUTH_ROUTE = "auth_graph" // Renomeei para maior clareza
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(
        startDestination = AuthScreen.Login.route,
        route = AppDestinations.AUTH_ROUTE
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                authlogonAction = {
                    // TODO()
                },
                signupAction = {
                    navController.navigate(AuthScreen.Register.route)
                },
            )
        }
        composable(route = AuthScreen.Register.route) {
            RegisterScreen(
                signupAction = {
                    // TODO()
                },
                loginAction = {
                    navController.popBackStack()
                },
            )
        }
    }
}