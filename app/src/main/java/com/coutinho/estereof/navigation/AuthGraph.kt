// src/main/java/com/coutinho/estereof/navigation/AuthGraph.kt
package com.coutinho.estereof.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coutinho.estereof.ui.auth.LoginScreen
import com.coutinho.estereof.ui.auth.RegisterScreen

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Register : AuthScreen("register")
}

object AuthDestinations {
    const val AUTH_ROUTE = "auth_graph"
}

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(
        startDestination = AuthScreen.Login.route,
        route = AuthDestinations.AUTH_ROUTE
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                signupAction = {
                    navController.navigate(AuthScreen.Register.route)
                },
                loginSuccessAction = {
                    navController.navigate(AppDestinations.APP_ROUTE) {
                        popUpTo(AuthDestinations.AUTH_ROUTE) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthScreen.Register.route) {
            RegisterScreen(
                loginAction = {
                    navController.popBackStack()
                },
                registerSuccessAction = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}