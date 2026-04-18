package com.skripsi.myapplication.feature.history.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.skripsi.myapplication.core.theme.TextSecondary
import com.skripsi.myapplication.core.theme.green
import com.skripsi.myapplication.model.HistoryItem

@Composable
fun HistoryItemRow(item: HistoryItem) {
    val (iconColor, bgColor, iconVector) = when (item.compartmentType.lowercase()) {
        "organik" -> Triple(green, Color(0xFFE8F5E9), R.drawable.ic_organic)
        "anorganik" -> Triple(Color(0xFFFFB300), Color(0xFFFFF8E1),  R.drawable.ic_anorganic)
        "b3" -> Triple(Color(0xFFE53935), Color(0xFFFFEBEE),  R.drawable.ic_b3)
        else -> Triple(Color.Gray, Color(0xFFF3F4F6),  R.drawable.ic_organic)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(bgColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconVector),
                contentDescription = item.compartmentType,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.subCategory,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                ),
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "EcoBin-RVM-Alpha • ${item.createdAt.substring(0, 10)}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextSecondary,
                    fontSize = 12.sp
                ),
                maxLines = 1,
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "+${item.pointsEarned}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = green
                )
            )
            Text(
                text = "Pts",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryItemRowPreview() {
    val mock = HistoryItem(1, "", 1, "organik", "Kulit Pisang (x2)", "2026-04-24T14:30:00.000Z")
    Box(modifier = Modifier.padding(16.dp)) {
        HistoryItemRow(mock)
    }
}
