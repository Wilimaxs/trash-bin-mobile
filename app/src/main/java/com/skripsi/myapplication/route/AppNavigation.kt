package com.skripsi.myapplication.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skripsi.myapplication.core.base.AuthState
import com.skripsi.myapplication.core.base.AuthViewModel
import com.skripsi.myapplication.feature.loading.LoadingScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    /**
     *
     * Default Route, Based from [AuthState] and [AuthViewModel]
     *
     */
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Loading -> {
                // Biarkan di loading, NavHost default bisa diatur ke sini
                navController.navigate(Screen.Loading.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
            is AuthState.Onboarding -> {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
            is AuthState.Authenticated -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    /**
     *
     * Navigation Graph - Definition of all routes/destinations in the application
     * 
     * Uses [Screen] as route definition and automatically navigates
     * based on [AuthState] changes from [AuthViewModel]
     * 
     * @see LaunchedEffect for automatic navigation logic
     *
     */
    NavHost(
        navController = navController,
        startDestination = Screen.Loading.route,
        modifier = modifier
    ) {
        composable(Screen.Loading.route) {
            LoadingScreen()
        }

        composable(Screen.Onboarding.route) {
            // TODO: Ganti dengan UI Onboarding asli
            // Sementara gunakan callback ini saat dummy selesai
            // OnboardingScreen(onFinish = { authViewModel.finishOnboarding() })
        }

        composable(Screen.Login.route) {
            // TODO: Ganti dengan UI Login asli
            // Sementara bisa memanggil authViewModel.setAuthenticated("token123") kalau sukses login
        }

        composable(Screen.Home.route) {
            // TODO: Ganti dengan UI Home/Main aplikasi
            // Sementara bisa memanggil authViewModel.logout() untuk tester logout
        }
    }
}
