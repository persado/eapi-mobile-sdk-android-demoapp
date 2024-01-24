package com.persado.sdkexample.data.repository

import com.persado.sdkexample.di.Dispatcher
import com.persado.sdkexample.di.PDispatchers
import com.persado.sdkexample.domain.model.Product
import com.persado.sdkexample.domain.model.User
import com.persado.sdkexample.network.datasource.FakeNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeDataRepository @Inject constructor(
    @Dispatcher(PDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FakeNetworkDataSource,
) : DataRepository {

    override fun getUsers(): Flow<List<User>> = flow {
        emit(datasource.getUsers())
    }.flowOn(ioDispatcher)

    override fun getProducts(): Flow<List<Product>> = flow {
        emit(datasource.getProducts())
    }.flowOn(ioDispatcher)
}