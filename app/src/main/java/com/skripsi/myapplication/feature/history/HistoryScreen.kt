package com.skripsi.myapplication.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import com.skripsi.myapplication.feature.history.composable.HistoryCategoryTabs
import com.skripsi.myapplication.feature.history.composable.HistoryHeader
import com.skripsi.myapplication.feature.history.composable.HistoryItemRow
import com.skripsi.myapplication.core.theme.SmartTrashBinTheme
import com.skripsi.myapplication.utils.snackbar.CustomSnackBarManager

@OptIn(androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val categories = listOf("All", "Organik", "Anorganik", "B3")

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            CustomSnackBarManager.showError(msg)
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.loadData(isRefresh = true) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            HistoryHeader(points = state.totalPoints)

            HistoryCategoryTabs(
                categories = categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = viewModel::onCategorySelected
            )

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val grouped = state.groupedHistory

                    if (grouped.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Tidak ada history terbaru",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    grouped.forEach { (month, items) ->
                        item {
                            Text(
                                text = month,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                        }

                        items(items) { historyItem ->
                            HistoryItemRow(item = historyItem)
                        }
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    } else if (state.currentPage < state.maxPage) {
                        // Trigger load more when reaching the end of a list
                        item {
                            LaunchedEffect(true) {
                                viewModel.loadMore()
                            }
                        }
                    }
                }
            }
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

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    SmartTrashBinTheme {
        HistoryScreen()
    }
}
