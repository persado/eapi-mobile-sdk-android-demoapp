package com.persado.sdkexample.data.repository

import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    fun getUsers(): Flow<List<User>>

    fun getProducts(): Flow<List<Product>>
}