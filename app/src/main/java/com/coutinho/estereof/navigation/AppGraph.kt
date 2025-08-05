package com.coutinho.estereof.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation // Importação necessária
import com.coutinho.estereof.ui.auth.LoginScreen
import com.coutinho.estereof.ui.auth.RegisterScreen
import com.coutinho.estereof.ui.categories.CategoriesScreen
import com.coutinho.estereof.ui.home.HomeScreen
import com.coutinho.estereof.ui.paymentmethods.PaymentMethodsScreen
import com.coutinho.estereof.ui.transactions.TransactionsScreen

sealed class AppScreen(val route: String) {
    object Home : AppScreen("Home")
    object Transactions : AppScreen("Transactions")
    object PaymentsMethods : AppScreen("PaymentsMethods")
    object Categories : AppScreen("Categories")
}

object AppDestinations {
    const val APP_ROUTE = "app_graph" // Renomeei para maior clareza
}

fun NavGraphBuilder.appGraph(
    navController: NavController
) {
    navigation(
        startDestination = AppScreen.Home.route,
        route = AppDestinations.APP_ROUTE
    ) {
        composable(
            route = AppScreen.Home.route
        ){
            HomeScreen()
        }
        composable(
            route = AppScreen.Transactions.route
        ){
            TransactionsScreen()
        }
        composable(
            route = AppScreen.PaymentsMethods.route
        ){
            PaymentMethodsScreen()
        }
        composable(
            route = AppScreen.Categories.route
        ){
            CategoriesScreen()
        }
    }
}