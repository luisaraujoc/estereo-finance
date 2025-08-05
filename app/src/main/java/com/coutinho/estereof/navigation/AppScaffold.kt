package com.coutinho.estereof.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define se a BottomNavBar deve ser mostrada
    val showBottomBar = currentRoute != AuthDestinations.AUTH_ROUTE && currentRoute != AppDestinations.APP_ROUTE

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