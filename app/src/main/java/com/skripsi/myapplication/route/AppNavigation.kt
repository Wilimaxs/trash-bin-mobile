package com.skripsi.myapplication.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.skripsi.myapplication.feature.login.LoginScreen
import com.skripsi.myapplication.feature.forgot.ForgotPasswordScreen
import com.skripsi.myapplication.feature.forgot.reset.ResetScreen
import com.skripsi.myapplication.feature.mainNavigation.MainNavigationScreen
import com.skripsi.myapplication.feature.onboarding.OnBoardingScreen
import com.skripsi.myapplication.feature.profile.help.HelpSupportScreen
import com.skripsi.myapplication.feature.profile.privacy.PrivacyScreen
import com.skripsi.myapplication.feature.registration.RegistrationScreen
import com.skripsi.myapplication.feature.verify.VerifyScreen
import com.skripsi.myapplication.utils.snackbar.CustomTopSnackBarHost

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        when (authState) {
            is AuthState.Loading -> {
                if (currentRoute != Screen.Loading.route) {
                    navController.navigate(Screen.Loading.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            is AuthState.Onboarding -> {
                if (currentRoute != Screen.Onboarding.route) {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            is AuthState.Unauthenticated -> {
                val unauthenticatedRoutes = listOf(
                    Screen.Login.route,
                    Screen.Registration.route,
                    Screen.Verify.route,
                    Screen.Forgot.route,
                    Screen.VerifyForgot.route,
                    Screen.ResetPassword.route
                )
                if (currentRoute !in unauthenticatedRoutes) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            is AuthState.Authenticated -> {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.Loading.route,
        ) {
            composable(Screen.Loading.route) {
                LoadingScreen()
            }

            composable(Screen.Onboarding.route) {
                OnBoardingScreen(
                    onGetStartedClick = {
                        authViewModel.finishOnboarding()
                    }
                )
            }

            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToSignUp = {
                        navController.navigate(Screen.Registration.route)
                    },
                    onNavigateToMainRoute = { token, refreshToken ->
                        authViewModel.setAuthenticated(token, refreshToken)
                    },
                    onNavigateToForgot = {
                        navController.navigate(Screen.Forgot.route)
                    }
                )
            }

            composable(Screen.Forgot.route) {
                ForgotPasswordScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToVerify = {
                        navController.navigate(Screen.VerifyForgot.route)
                    }
                )
            }

            composable(Screen.VerifyForgot.route) {
                VerifyScreen(
                    isForgotFlow = true,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToResetPassword = {
                        navController.navigate(Screen.ResetPassword.route) {
                            popUpTo(Screen.Forgot.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.ResetPassword.route) {
                ResetScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Registration.route) {
                RegistrationScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToVerify = {
                        navController.navigate(Screen.Verify.route)
                    }
                )
            }

            composable(Screen.Verify.route) {
                VerifyScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                MainNavigationScreen(
                    onLogout = {
                        authViewModel.logout()
                    },
                    onNavigateToPrivacyPolicy = {
                        navController.navigate(Screen.PrivacyPolicy.route)
                    },
                    onNavigateToHelpSupport = {
                        navController.navigate(Screen.HelpSupport.route)
                    },
                )
            }

            composable(Screen.HelpSupport.route) {
                HelpSupportScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.PrivacyPolicy.route) {
                PrivacyScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

        }
        CustomTopSnackBarHost()
    }
}
