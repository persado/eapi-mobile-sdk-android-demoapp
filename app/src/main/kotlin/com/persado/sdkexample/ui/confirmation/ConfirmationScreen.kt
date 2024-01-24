package com.persado.sdkexample.ui.confirmation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.persado.sdkexample.R
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.ui.landing.MainViewModel
import com.persado.sdkexample.ui.theme.CheckGreen
import com.persado.sdkexample.ui.util.TotalRow
import com.persado.sdkexample.util.format

@Composable
fun ConfirmationScreen(
    onBackToShop: () -> Unit,
    mainViewModel: MainViewModel
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val total = uiState.cartContent.total

    BackHandler {
        mainViewModel.emptyCart()
        onBackToShop()
    }

    LaunchedEffect(key1 = Unit) {
        mainViewModel.trackPersadoConvert()
    }

    Column {
        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier.size(84.dp),
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = stringResource(R.string.txt_empty_cart),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary),
                    painter = painterResource(id = R.drawable.check_circle),
                    contentDescription = null,
                    tint = CheckGreen
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.txt_order_success),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        .toSpanStyle()
                ) {
                    append(stringResource(R.string.txt_estimated_delivery))
                }
                append(" ")
                withStyle(
                    MaterialTheme.typography.bodyMedium.toSpanStyle()
                ) {
                    append(uiState.estimatedDeliveryDate)
                }
            }.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
                .verticalScroll(rememberScrollState())
                .weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = stringResource(R.string.txt_order_number, uiState.orderNumber),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            uiState.cart.forEach {
                ProductRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp), product = it
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            Totals(
                modifier = Modifier,
                totalLabel = total,
                items = uiState.cart
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            // Back to shop
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RectangleShape,
                onClick = {
                    mainViewModel.emptyCart()
                    onBackToShop()
                },
            )
            {
                Text(
                    text = stringResource(R.string.button_back_to_shop),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun ProductRow(
    modifier: Modifier = Modifier,
    product: Product,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = product.discountPrice.format(2),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun Totals(
    modifier: Modifier = Modifier,
    totalLabel: String,
    items: List<Product>
) {
    val subTotal = items.sumOf { it.discountPrice }
    val shipping = if (items.isNotEmpty()) 25.0 else 0.0
    val total = subTotal + shipping

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TotalRow(text = stringResource(id = R.string.txt_sub_total), value = subTotal)
        TotalRow(text = stringResource(id = R.string.txt_shipping), value = shipping)
        TotalRow(
            text = totalLabel,
            value = total,
            grandTotal = true
        )
    }
}