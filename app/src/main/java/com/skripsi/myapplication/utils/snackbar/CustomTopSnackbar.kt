package com.skripsi.myapplication.utils.snackbar

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skripsi.myapplication.R
import kotlinx.coroutines.delay

@Composable
fun CustomTopSnackBarHost(
    modifier: Modifier = Modifier
) {
    val snackBarData by CustomSnackBarManager.snackBarState.collectAsState()

    LaunchedEffect(snackBarData) {
        snackBarData?.let {
            delay(2000L)
            CustomSnackBarManager.dismiss()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        AnimatedVisibility(
            visible = snackBarData != null,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300)),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount < -5) {
                            CustomSnackBarManager.dismiss()
                        }
                    }
                }
        ) {
            snackBarData?.let { data ->
                val (backgroundColor, iconResId) = when (data.type) {
                    SnackBarType.SUCCESS -> Pair(Color(0xFF4CAF50), R.drawable.ic_success)
                    SnackBarType.ERROR -> Pair(Color(0xFFE53935), R.drawable.ic_error)
                    SnackBarType.INFO -> Pair(Color(0xFF1E88E5), R.drawable.ic_info)
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = backgroundColor,
                    shadowElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = iconResId),
                            contentDescription = data.type.name,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = data.message,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
    }
}
