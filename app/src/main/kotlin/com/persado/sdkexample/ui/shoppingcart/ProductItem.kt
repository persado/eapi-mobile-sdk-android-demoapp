package com.persado.sdkexample.ui.shoppingcart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissValue
import androidx.compose.material3.rememberSwipeToDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.persado.sdkexample.domain.model.Product
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    product: Product,
    onRemove: (Product) -> Unit,
) {
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(product)
    val density = LocalDensity.current
    val dismissState = rememberSwipeToDismissState(
        confirmValueChange = { dismissedValue ->
            if (dismissedValue == SwipeToDismissValue.EndToStart) {
                show = false
                true
            } else false
        },
        positionalThreshold = { with(density) { 200.dp.toPx() } }
    )
    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismissBox(
            modifier = Modifier,
            state = dismissState,
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                DismissBackground(dismissState)
            },
            content = {
                ProductRow(modifier = Modifier.padding(top = 8.dp), product = product)
            }
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(800)
            onRemove(currentItem)
        }
    }
}