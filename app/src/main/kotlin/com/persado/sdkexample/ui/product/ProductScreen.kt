package com.persado.sdkexample.ui.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.persado.sdkexample.R
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.ui.landing.MainViewModel
import com.persado.sdkexample.ui.util.BackButton
import com.persado.sdkexample.ui.util.PricesRow
import com.persado.sdkexample.ui.util.StarsRow
import com.persado.sdkexample.util.format

@Composable
fun ProductScreen(
    onContinueShopping: () -> Unit = {},
    onCheckout: () -> Unit = {},
    mainViewModel: MainViewModel
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.selectedProduct
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f),
            ) {
                product?.let { ProductDetails(product = it) }
            }

            // CTA button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RectangleShape,
                onClick = {
                    product?.let {
                        mainViewModel.addToCart(it)
                        showBottomSheet = true
                    }
                })
            {
                Text(
                    text = stringResource(R.string.button_add_to_cart),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    BackButton {
        onContinueShopping()
    }

    if (showBottomSheet) {
        val remainingProducts = uiState.products.filter { it != product }
        val (first, second) = (remainingProducts.indices).shuffled().take(2)

        BottomSheet(
            suggestedProducts = Pair(remainingProducts[first], remainingProducts[second]),
            onDismiss = {
                showBottomSheet = false
            },
            onProductClick = {
                showBottomSheet = false
                mainViewModel.selectProduct(it)
            },
            onContinueShopping = {
                showBottomSheet = false
                onContinueShopping()
            },
            onCheckout = {
                showBottomSheet = false
                onCheckout()
            }
        )
    }
}

@Composable
fun ProductDetails(
    product: Product,
) {
    AsyncImage(
        modifier = Modifier
            .height(350.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.FillWidth,
        contentDescription = product.name
    )

    val detailsModifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ProductHeader(modifier = detailsModifier, product = product)

    StarsRow(rating = product.rating, modifier = detailsModifier)

    PricesRow(
        modifier = detailsModifier,
        msrp = product.msrp.format(2),
        mrspStyle = MaterialTheme.typography.bodyLarge,
        discountPrice = product.discountPrice.format(2),
        discountStyle = MaterialTheme.typography.titleMedium
    )

    Divider(modifier = detailsModifier)

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        text = product.description,
        style = MaterialTheme.typography.bodyMedium,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun ProductHeader(product: Product, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = product.name,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        modifier = modifier,
        text = product.category + " > " + product.itemType,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary
    )
}