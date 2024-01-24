package com.persado.sdkexample.network.datasource

import java.io.InputStream

fun interface FakeAssetManager {
    fun open(filename: String): InputStream
}
