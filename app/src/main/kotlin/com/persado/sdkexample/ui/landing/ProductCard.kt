package com.persado.sdkexample.ui.landing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.ui.theme.PTheme
import com.persado.sdkexample.ui.util.PricesRow
import com.persado.sdkexample.ui.util.StarsRow
import com.persado.sdkexample.util.format

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    imageWeight: Float,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(imageWeight),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = product.name
            )
            Column(
                modifier = Modifier
                    .weight(1f - imageWeight)
                    .padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = product.name,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = product.category + " > " + product.itemType,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        StarsRow(rating = product.rating)

                        PricesRow(
                            msrp = product.msrp.format(2),
                            mrspStyle = MaterialTheme.typography.bodySmall,
                            discountPrice = product.discountPrice.format(2),
                            discountStyle = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProductCard() {
    PTheme {
        ProductCard(
            modifier = Modifier.height(230.dp),
            product = Product(
                id = "Product 1",
                name = "Luxe Harmony Sofa",
                description = "Immerse yourself in unparalleled comfort with our Luxe Harmony Sofa. This modern masterpiece combines plush, velvet upholstery with sleek metal accents, creating a statement piece that redefines relaxation. Sink into the deep cushions and let the world fade away as you indulge in the perfect blend of style and coziness.",
                discountPrice = 925.0,
                msrp = 1250.0,
                rating = 4.5f,
                itemType = "Sofa",
                category = "Living Room",
                imageUrl = "https://us-dmdemo.persadolabs.net/images/product-large-1.jpg"
            ),
            imageWeight = 0.35f
        )
    }
}