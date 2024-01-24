package com.persado.sdkexample.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val discountPrice: Double,
    val msrp: Double,
    val rating: Float,
    val itemType: String,
    val category: String,
    val imageUrl: String
)

