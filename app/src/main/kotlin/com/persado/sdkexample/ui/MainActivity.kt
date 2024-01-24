package com.persado.sdkexample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.persado.sdkexample.data.repository.FakeDataRepository
import com.persado.sdkexample.network.PersadoManager
import com.persado.sdkexample.ui.theme.PTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var persadoManager: PersadoManager

    @Inject
    lateinit var dataRepository: FakeDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PTheme {
                PApp(dataRepository, persadoManager)
            }
        }
    }
}