package com.persado.sdkexample.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LandingScreen(
    onItemSelected: () -> Unit = {},
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val size = uiState.products.size
        if (size > 0) {
            item(key = -1, span = { GridItemSpan(maxLineSpan) }) {
                ProductCard(
                    modifier = Modifier.height(250.dp),
                    product = uiState.products[0],
                    imageWeight = 0.65f
                ) {
                    mainViewModel.selectProduct(uiState.products[0])
                    onItemSelected()
                }
            }
        }

        if (size > 1) {
            items(
                count = size - 1,
                key = { index -> uiState.products[index].id },
                span = { GridItemSpan(1) }
            ) {
                ProductCard(
                    modifier = Modifier.height(180.dp),
                    product = uiState.products[it + 1],
                    imageWeight = 0.5f
                ) {
                    mainViewModel.selectProduct(uiState.products[it + 1])
                    onItemSelected()
                }
            }
        }
    }
}