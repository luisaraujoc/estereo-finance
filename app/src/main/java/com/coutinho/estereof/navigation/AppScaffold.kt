// src/main/java/com/coutinho/estereof/navigation/AppScaffold.kt
package com.coutinho.estereof.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Importe a constante AUTH_ROUTE do arquivo AuthGraph.kt
import com.coutinho.estereof.navigation.AuthDestinations.AUTH_ROUTE

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define se a BottomNavBar deve ser mostrada
    val showBottomBar = currentRoute != AUTH_ROUTE && currentRoute != AppDestinations.APP_ROUTE

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = defineStartDestination(),
            modifier = Modifier.padding(innerPadding)
        ) {
            authGraph(navController)
            appGraph(navController)
        }
    }
}

// Corrija a função para retornar uma String e usar a palavra-chave 'return'
fun defineStartDestination(): String {
    val isUserLoggedIn = true

    return if (!isUserLoggedIn) {
        AUTH_ROUTE // Usando a constante importada
    } else {
        AppDestinations.APP_ROUTE
    }
}