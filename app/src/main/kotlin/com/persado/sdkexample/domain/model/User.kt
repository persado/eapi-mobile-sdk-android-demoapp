package com.persado.sdkexample.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val city: String,
    val country: String,
    val gender: String,
    val maritalStatus: String,
    val loyaltyStatus: String,
    val accountStatus: String,
    val currency: String,
    val accountId: String,
    val deviceType: String,
    val browser: String
)
