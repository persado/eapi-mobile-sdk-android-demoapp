package com.persado.sdkexample.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.persado.sdkexample.R
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.ui.landing.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    suggestedProducts: Pair<Product, Product>,
    onDismiss: () -> Unit,
    onProductClick: (Product) -> Unit,
    onContinueShopping: () -> Unit,
    onCheckout: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = {
            onDismiss()
        },
        sheetState = modalBottomSheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.txt_added_to_cart),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.txt_message_interested),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )

            Row {
                ProductCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(220.dp)
                        .padding(top = 16.dp),
                    product = suggestedProducts.first,
                    imageWeight = 0.6f
                ) {
                    onProductClick(suggestedProducts.first)
                }

                Spacer(modifier = Modifier.width(16.dp))

                ProductCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(220.dp)
                        .padding(top = 16.dp),
                    product = suggestedProducts.second,
                    imageWeight = 0.6f
                ) {
                    onProductClick(suggestedProducts.second)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        // Continue Shopping
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = {
                onContinueShopping()
            },
        )
        {
            Text(
                text = stringResource(R.string.button_continue_shopping),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Checkout
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RectangleShape,
            onClick = {
                onCheckout()
            },
        )
        {
            Text(
                text = stringResource(R.string.button_checkout),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(
            Modifier.navigationBarsPadding()
        )
    }
}