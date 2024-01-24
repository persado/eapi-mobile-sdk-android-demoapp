package com.persado.sdkexample.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.persado.sdkexample.R
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.util.format

@Composable
fun Totals(
    modifier: Modifier = Modifier,
    totalLabel: String,
    items: List<Product>
) {
    val subTotal = items.sumOf { it.discountPrice }
    val shipping = if (items.isNotEmpty()) 25.0 else 0.0
    val total = subTotal + shipping

    Card(
        modifier = modifier
            .shadow(ambientColor = Color.Blue, elevation = 15.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = modifier.padding(16.dp),
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
}

@Composable
fun TotalRow(
    modifier: Modifier = Modifier,
    text: String,
    value: Double,
    grandTotal: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = if (grandTotal) MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            else MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = value.format(2),
            style =
            if (grandTotal) MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            else MaterialTheme.typography.bodyMedium,
        )
    }
}

