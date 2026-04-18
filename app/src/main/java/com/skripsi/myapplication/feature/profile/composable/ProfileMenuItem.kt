package com.skripsi.myapplication.feature.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.TextPrimary

@Composable
fun ProfileMenuItem(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0xFFF3F4F6), shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp)), // Light green bg
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                tint = Color(0xFF0F4A24), // Dark green icon
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                fontSize = 16.sp
            ),
            modifier = Modifier.weight(1f)
        )

        // Chevron Right
        Icon(
           painter = painterResource(R.drawable.ic_right_arrow),
            contentDescription = "Arrow Right",
            tint = Color(0xFF9CA3AF) // Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileMenuItemPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        ProfileMenuItem(
            icon = R.drawable.ic_help,
            title = "Edit Profile",
            onClick = {}
        )
    }
}

