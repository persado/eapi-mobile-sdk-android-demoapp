package com.persado.sdkexample.ui.navigation

import com.persado.sdkexample.R

enum class TopLevelDestination(
    val titleTextId: Int,
) {
    LANDING(
        titleTextId = R.string.title_products
    ),
    SHOPPING_CART(
        titleTextId = R.string.title_shopping_cart
    ),
}
