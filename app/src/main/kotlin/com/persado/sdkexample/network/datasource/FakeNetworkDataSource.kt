package com.persado.sdkexample.network.datasource

import com.persado.sdkexample.di.Dispatcher
import com.persado.sdkexample.di.PDispatchers
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class FakeNetworkDataSource @Inject constructor(
    @Dispatcher(PDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager,
) : DNetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getUsers(): List<User> =
        withContext(ioDispatcher) {
            assets.open(USERS_ASSET).use(networkJson::decodeFromStream)
        }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getProducts(): List<Product> =
        withContext(ioDispatcher) {
            assets.open(PRODUCTS_ASSET).use(networkJson::decodeFromStream)
        }

    companion object {
        private const val USERS_ASSET = "users.json"
        private const val PRODUCTS_ASSET = "products.json"
    }
}