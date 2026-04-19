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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.TextPrimary
import com.skripsi.myapplication.core.theme.TextSecondary
import com.skripsi.myapplication.feature.profile.composable.ProfileCard
import com.skripsi.myapplication.feature.profile.composable.ProfileMenuItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogoutNavigate: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                color = TextPrimary
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
                    color = TextSecondary,
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
            onClick = { /* TODO */ }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ProfileMenuItem(
            icon = R.drawable.ic_help,
            title = "Help & Support",
            onClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Logout Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    viewModel.onLogoutClick(onLogoutSuccess = onLogoutNavigate)
                }
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logout),
                contentDescription = "Log Out",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log Out",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEF4444),
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // App Version
        Text(
            text = "App Version 2.4.0",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF9CA3AF), // Abu-abu
            )
        )

        Spacer(modifier = Modifier.height(10.dp)) // Extra space for Bottom Navigation
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}
