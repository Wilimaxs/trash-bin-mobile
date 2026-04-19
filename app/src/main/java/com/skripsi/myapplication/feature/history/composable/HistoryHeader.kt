package com.skripsi.myapplication.feature.history.composable

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.green

@Composable
fun HistoryHeader(points: Int) {
    Column {
        Text(
            text = "History",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = green, shape = RoundedCornerShape(12.dp))
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_point_earned),
                    contentDescription = "Points",
                    tint = Color(0xFF0F4A24)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Points Earned",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF0F4A24),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = points.toString(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color(0xFF0F4A24),
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Pts",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF0F4A24),
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryHeaderPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        HistoryHeader(725)
    }
}

