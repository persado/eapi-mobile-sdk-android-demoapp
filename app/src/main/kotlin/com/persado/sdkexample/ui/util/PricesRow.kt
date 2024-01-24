package com.persado.sdkexample.ui.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.persado.sdkexample.ui.theme.PriceGray

@Composable
fun PricesRow(
    modifier: Modifier = Modifier,
    msrp: String,
    mrspStyle: TextStyle = MaterialTheme.typography.bodySmall,
    discountPrice: String,
    discountStyle: TextStyle = MaterialTheme.typography.bodySmall
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = msrp,
            style = mrspStyle.copy(
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.LineThrough
            ),
            color = PriceGray
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = discountPrice,
            style = discountStyle.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}