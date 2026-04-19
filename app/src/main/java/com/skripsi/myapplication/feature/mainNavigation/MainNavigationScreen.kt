package com.skripsi.myapplication.feature.mainNavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.skripsi.myapplication.core.theme.green
import com.skripsi.myapplication.feature.history.HistoryScreen
import com.skripsi.myapplication.feature.home.HomeScreen
import com.skripsi.myapplication.feature.profile.ProfileScreen

@Composable
fun MainNavigationScreen() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    val isSelected = currentRoute == item.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(if (isSelected) item.selectedIcon else item.unselectedIcon),
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = green,
                            selectedTextColor = green,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }
            composable(BottomNavItem.History.route) {
                HistoryScreen()
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    onLogoutNavigate = {
                        // TODO: Bersihkan auth / route ke layar login di sini
                        navController.navigate("login_screen") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainNavigationScreenPreview() {
    MainNavigationScreen()
}
