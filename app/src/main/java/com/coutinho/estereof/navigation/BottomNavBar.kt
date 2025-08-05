// src/main/java/com/coutinho/estereof/navigation/BottomNavBar.kt
package com.coutinho.estereof.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coutinho.estereof.R
import com.coutinho.estereof.ui.theme.spaceGroteskFamily
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.BarChart
import compose.icons.evaicons.fill.CreditCard
import compose.icons.evaicons.fill.Folder
import compose.icons.evaicons.fill.Home
import compose.icons.evaicons.fill.PieChart
import compose.icons.evaicons.outline.BarChart
import compose.icons.evaicons.outline.CreditCard
import compose.icons.evaicons.outline.Folder
import compose.icons.evaicons.outline.Home
import compose.icons.evaicons.outline.PieChart

// Mapeia cada tela da barra de navegação com suas rotas, ícones e strings.
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val titleResId: Int
)
{
    object Home : BottomNavItem(
        AppScreen.Home.route,
        EvaIcons.Outline.Home,
        EvaIcons.Fill.Home,
        R.string.bottom_nav_home
    )
    object Transactions : BottomNavItem(
        AppScreen.Transactions.route,
        EvaIcons.Outline.BarChart,
        EvaIcons.Fill.BarChart,
        R.string.bottom_nav_transactions
    )
    object Categories : BottomNavItem(
        AppScreen.Categories.route,
        EvaIcons.Outline.Folder,
        EvaIcons.Fill.Folder,
        R.string.bottom_nav_categories
    )
    object PaymentMethods : BottomNavItem(
        AppScreen.PaymentsMethods.route,
        EvaIcons.Outline.CreditCard,
        EvaIcons.Fill.CreditCard,
        R.string.bottom_nav_payment_methods
    )
}

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Transactions,
        BottomNavItem.Categories,
        BottomNavItem.PaymentMethods
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(0.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                ),
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = stringResource(id = item.titleResId),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.titleResId),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}