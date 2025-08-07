// src/main/java/com/coutinho/estereof/navigation/AppScaffold.kt
package com.coutinho.estereof.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coutinho.estereof.data.DatabaseProvider
import com.coutinho.estereof.navigation.AuthDestinations.AUTH_ROUTE

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val userDao = remember { DatabaseProvider.getDatabase(context).userDao() }

    // Use um estado para armazenar a rota inicial
    var startDestination by remember { mutableStateOf<String?>(null) }

    // Lançar uma corrotina para carregar o estado de login
    LaunchedEffect(key1 = Unit) {
        val userCount = userDao.getUserCount()
        startDestination = if (userCount > 0) {
            AppDestinations.APP_ROUTE
        } else {
            AUTH_ROUTE
        }
    }

    // Enquanto a rota inicial não for definida, mostre uma tela de carregamento
    if (startDestination == null) {
        return
    }

    // Define se a BottomNavBar deve ser mostrada
    // A barra deve ser mostrada se a rota atual for uma das telas principais do aplicativo
    val showBottomBar = currentRoute in setOf(
        AppScreen.Home.route,
        AppScreen.Transactions.route,
        AppScreen.Categories.route,
        AppScreen.PaymentsMethods.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination!!,
            modifier = Modifier.padding(innerPadding)
        ) {
            authGraph(navController)
            appGraph(navController)
        }
    }
}