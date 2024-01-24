package com.persado.sdkexample.ui.shoppingcart

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.persado.sdkexample.ui.landing.MainViewModel
import com.persado.sdkexample.ui.util.Totals
import java.util.UUID

@Composable
fun ShoppingCartScreen(
    onCta: () -> Unit,
    mainViewModel: MainViewModel
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val cart = uiState.cart

    // Cart Content from Persado EAPI
    val total = uiState.cartContent.total
    val cta = uiState.cartContent.cta

    LaunchedEffect(key1 = Unit) {
        mainViewModel.trackPersadoView()
    }

    LaunchedEffect(key1 = uiState.orderCompleted) {
        if (uiState.orderCompleted) {
            onCta()
        }
    }

    BackHandler(
        enabled = uiState.sendingOrder
    ) {

    }

    val modifier = Modifier
        .fillMaxSize()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            if (cart.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    items(key = { UUID.randomUUID() }, count = cart.size) {
                        ProductItem(
                            product = cart[it],
                            onRemove = { product ->
                                mainViewModel.removeFromCard(product)
                            }
                        )

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Totals(
                        modifier = Modifier,
                        totalLabel = total,
                        items = cart
                    )

                    // CTA Purchase
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RectangleShape,
                        enabled = !uiState.sendingOrder,
                        onClick = {
                            mainViewModel.completePurchase()
                            mainViewModel.trackPersadoClick()
                        },
                    )
                    {
                        Text(
                            text = cta,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                EmptyCart()
            }
        }
    }

    if (uiState.sendingOrder) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {},
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

