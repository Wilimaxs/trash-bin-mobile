package com.skripsi.myapplication.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skripsi.myapplication.feature.home.HomeState

import androidx.compose.foundation.BorderStroke

@Composable
fun ConnectedContent(
    state: HomeState,
    onStopClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(bottom = 12.dp),
    ) {
        item {
            Text(
                text = "RVM Monitor",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 24.dp).fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        item {
            // Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8FDF0), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = state.rvmName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF102218)
                        )
                        Text(
                            text = "Connected on: ${state.connectedOn}",
                            fontSize = 14.sp,
                            color = Color(0xFF4CA771)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFF13EC6D), RoundedCornerShape(16.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF13EC6D), RoundedCornerShape(4.dp))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CONNECTED",
                                color = Color(0xFF13EC6D),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            // Status bins placeholder
            Text(
                text = "Status EcoBin",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusBinItem(title = "ORGANIK", percent = state.organicPercent, color = Color(0xFF13EC6D))
                StatusBinItem(title = "ANORGANIK", percent = state.anorganicPercent, color = Color(0xFFFFB020))
                StatusBinItem(title = "B3", percent = state.b3Percent, color = Color(0xFFFF3B30))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Live Activity",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8FDF0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color(0xFF13EC6D), RoundedCornerShape(3.dp))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LIVE UPDATE",
                            color = Color(0xFF13EC6D),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Dummy live activities
        items(4) { _ ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // icon circle placeholder
                        Box(modifier = Modifier.size(40.dp).background(Color(0xFFE8FDF0), RoundedCornerShape(20.dp)))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Item Name (x1)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                            Text(text = "Category • Just now", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Box(modifier = Modifier.background(Color(0xFFE8FDF0), RoundedCornerShape(16.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text(text = "+1 Pts", color = Color(0xFF13EC6D), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(modifier = Modifier.weight(1f), title = "TOTAL ITEMS", value = state.totalItems.toString(), suffix = "pcs")
                StatCard(modifier = Modifier.weight(1f), title = "TOTAL POINTS", value = state.totalPoints.toString(), suffix = "pts")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = onStopClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF2F2)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(text = "Stop Monitoring", color = Color(0xFFFF3B30), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun StatusBinItem(title: String, percent: Int, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        modifier = Modifier.width(100.dp).aspectRatio(0.8f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(60.dp)) {
                CircularProgressIndicator(
                    progress = { percent / 100f },
                    color = color,
                    trackColor = Color(0xFFF3F4F6),
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 4.dp
                )
                Text(text = "$percent%", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.size(6.dp).background(color, RoundedCornerShape(3.dp)))
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, suffix: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = suffix, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 2.dp))
            }
        }
    }
}




