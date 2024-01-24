package com.persado.sdkexample.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartContent(
    val headline: String = "",
    val total: String = "",
    val cta: String = ""
)
