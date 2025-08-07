// src/main/java/com/coutinho/estereof/navigation/AppGraph.kt
package com.coutinho.estereof.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.ui.auth.LoginScreen
import com.coutinho.estereof.ui.auth.RegisterScreen
import com.coutinho.estereof.ui.categories.CategoriesScreen
import com.coutinho.estereof.ui.home.HomeScreen
import com.coutinho.estereof.ui.paymentmethods.PaymentMethodsScreen
import com.coutinho.estereof.ui.transactions.NewTransactionScreen
import com.coutinho.estereof.ui.transactions.TransactionsScreen
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

sealed class AppScreen(val route: String) {
    object Home : AppScreen("Home")
    object Transactions : AppScreen("Transactions")
    object PaymentsMethods : AppScreen("PaymentsMethods")
    object Categories : AppScreen("Categories")

    // FAB actions
    object AddTransaction : AppScreen("AddTransaction")
}

object AppDestinations {
    const val APP_ROUTE = "app_graph"
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
            val context = LocalContext.current
            val userDao = remember { DatabaseProvider.getDatabase(context).userDao() }
            val userRepository = remember { UserRepository(userDao) }
            val coroutineScope = rememberCoroutineScope()

            HomeScreen(
                onLogout = {
                    coroutineScope.launch {
                        userRepository.delete(userRepository.getAll().firstOrNull()?.firstOrNull()!!)
                        navController.navigate(AuthDestinations.AUTH_ROUTE) {
                            popUpTo(AppDestinations.APP_ROUTE) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
        composable(
            route = AppScreen.Transactions.route
        ){
            TransactionsScreen(
                addTransactionAction = {
                    navController.navigate(AppScreen.AddTransaction.route)
                }
            )
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

        // FAB Screens
        composable(
            route = AppScreen.AddTransaction.route
        ){
            NewTransactionScreen(
                onCloseClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}