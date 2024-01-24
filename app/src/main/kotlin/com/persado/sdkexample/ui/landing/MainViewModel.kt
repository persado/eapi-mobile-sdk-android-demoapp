package com.persado.sdkexample.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.persado.sdkexample.data.repository.FakeDataRepository
import com.persado.sdkexample.network.PersadoManager
import com.persado.sdkexample.domain.model.CartContent
import com.persado.sdkexample.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataRepository: FakeDataRepository,
    private val persadoManager: PersadoManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getProducts().collect { products ->
                _uiState.update {
                    it.copy(products = products)
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            persadoManager.cartContent.collect { cartContent ->
                _uiState.update {
                    it.copy(cartContent = cartContent)
                }
            }
        }
    }

    private fun createOrder(product: Product) {
        val orderNumber = (100000..999999).random()

        val date = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 3)
        }.time
        val estimatedDeliveryDate =
            SimpleDateFormat("MMM dd,yyyy", Locale.getDefault()).format(date)

        _uiState.update {
            it.copy(
                estimatedDeliveryDate = estimatedDeliveryDate,
                orderNumber = orderNumber,
                cart = listOf(product)
            )
        }
    }

    fun selectProduct(product: Product) {
        _uiState.update {
            it.copy(selectedProduct = product)
        }
    }

    fun addToCart(product: Product) {
        if (_uiState.value.cart.isEmpty()) {
            createOrder(product)
        } else {
            _uiState.update {
                it.copy(cart = it.cart + product)
            }
        }
    }

    fun removeFromCard(product: Product) {
        _uiState.update {
            it.copy(cart = it.cart - product)
        }
    }

    fun emptyCart() {
        _uiState.update {
            it.copy(
                cart = emptyList(),
                orderCompleted = false
            )
        }
    }

    fun completePurchase() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(sendingOrder = true)
            }
            delay(2000)
            _uiState.update {
                it.copy(sendingOrder = false, orderCompleted = true)
            }
        }
    }

    fun trackPersadoView() {
       persadoManager.trackView()
    }

    fun trackPersadoClick() {
       persadoManager.trackClick()
    }

    fun trackPersadoConvert() {
       persadoManager.trackConvert()
    }
}

data class UiState(
    val products: List<Product> = emptyList(),
    val selectedProduct: Product? = null,
    val cart: List<Product> = emptyList(),
    val cartContent: CartContent = CartContent(),
    val estimatedDeliveryDate: String = "",
    val orderNumber: Int = 0,
    val sendingOrder: Boolean = false,
    val orderCompleted: Boolean = false
)

