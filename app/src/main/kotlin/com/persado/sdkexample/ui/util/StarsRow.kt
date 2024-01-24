package com.persado.sdkexample.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.persado.sdkexample.R
import com.persado.sdkexample.ui.theme.StarColor

@Composable
fun StarsRow(
    modifier: Modifier = Modifier,
    rating: Float
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val fullStars = rating.toInt()
        val hasHalfStar = rating - fullStars >= 0.5

        for (i in 1..5) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(
                    id = when {
                        i <= fullStars -> R.drawable.star
                        i == fullStars + 1 && hasHalfStar -> R.drawable.star_half
                        else -> R.drawable.star_empty
                    }
                ),
                tint = StarColor,
                contentDescription = null
            )
        }
    }
}
