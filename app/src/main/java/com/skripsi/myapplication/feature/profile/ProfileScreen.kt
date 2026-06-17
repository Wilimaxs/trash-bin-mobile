package com.skripsi.myapplication.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.feature.profile.composable.ProfileCard
import com.skripsi.myapplication.feature.profile.composable.ProfileMenuItem
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager

@OptIn(androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogoutNavigate: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit,
    onNavigateToHelpSupport: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            CustomSnackBarManager.showError(msg)
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.refreshProfileData() }
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "My Profile",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth().height(180.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    ProfileCard(profileData = state.profileData)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Account Settings TIdle
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "ACCOUNT SETTINGS",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Menus
                ProfileMenuItem(
                    icon = R.drawable.ic_edit,
                    title = "Edit Profile",
                    onClick = { /* TODO */ }
                )
                Spacer(modifier = Modifier.height(12.dp))
                ProfileMenuItem(
                    icon = R.drawable.ic_privacy,
                    title = "Privacy Policy",
                    onClick = onNavigateToPrivacyPolicy
                )
                Spacer(modifier = Modifier.height(12.dp))
                ProfileMenuItem(
                    icon = R.drawable.ic_help,
                    title = "Help & Support",
                    onClick = onNavigateToHelpSupport
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Logout Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            viewModel.onLogoutClick(onLogoutSuccess = {
                                CustomSnackBarManager.showSuccess("Success logout")
                                onLogoutNavigate()
                            })
                        }
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_logout),
                        contentDescription = "Log Out",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log Out",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App Version
                Text(
                    text = "App Version 2.4.0",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SmartTrashBinTheme {
        ProfileScreen(
            viewModel = hiltViewModel(),
            onLogoutNavigate = {},
            onNavigateToPrivacyPolicy = {},
            onNavigateToHelpSupport = {}
        )
    }
}
