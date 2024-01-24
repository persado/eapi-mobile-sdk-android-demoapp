package com.persado.sdkexample.network.datasource

import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.domain.model.User

interface DNetworkDataSource {

    suspend fun getUsers(): List<User>

    suspend fun getProducts(): List<Product>
}