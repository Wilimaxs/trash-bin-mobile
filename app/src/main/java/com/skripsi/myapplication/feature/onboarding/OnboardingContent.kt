package com.skripsi.myapplication.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skripsi.myapplication.R
import com.skripsi.myapplication.core.theme.TextSecondary
import com.skripsi.myapplication.core.theme.White

@Composable
fun OnboardingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(shape = RoundedCornerShape(size = 16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding),
                contentDescription = "Recycle and Earn Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(all = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(percent = 50))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_recycle),
                    contentDescription = "Recycle and Earn icon",
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SMART RVM",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                text = "Recycle & Earn",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(all = 24.dp)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Turn Trash into Treasure",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 27.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Join the EcoBin community to easily locate smart bins, recycle your waste, and earn exciting rewards for every bottle and can you save.",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboardingContent() {
    MaterialTheme {
        OnboardingContent()
    }
}
