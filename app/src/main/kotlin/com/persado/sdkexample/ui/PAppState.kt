package com.persado.sdkexample.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.persado.sdkexample.data.repository.DataRepository
import com.persado.sdkexample.network.PersadoManager
import com.persado.sdkexample.domain.model.CartContent
import com.persado.sdkexample.domain.model.User
import com.persado.sdkexample.ui.navigation.Screen
import com.persado.sdkexample.ui.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberPAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    dataRepository: DataRepository,
    persadoManager: PersadoManager
): PAppState {
    return remember(
        navController,
    ) {
        PAppState(
            navController,
            coroutineScope,
            dataRepository,
            persadoManager
        )
    }
}

@Stable
class PAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    dataRepository: DataRepository,
    private val persadoManager: PersadoManager
) {

    var user: User? = null

    private var _itemsCount = MutableStateFlow(0)
    val itemsCount: StateFlow<Int> = _itemsCount

    private var _sendingOrder = MutableStateFlow(false)
    val sendingOrder: StateFlow<Boolean> = _sendingOrder

    val cartContent: StateFlow<CartContent> = persadoManager.cartContent
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = CartContent(),
        )

    val users: StateFlow<List<User>> = dataRepository.getUsers()
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            Screen.Landing.route -> TopLevelDestination.LANDING
            Screen.ShoppingCart.route -> TopLevelDestination.SHOPPING_CART
            else -> null
        }

    val showShoppingCart: Boolean
        @Composable get() = currentDestination?.route != Screen.Landing.route

    fun onCartChanged(items: Int) {
        _itemsCount.value = items
    }

    fun onUserChange(user: User) {
        this.user = user
        persadoManager.initProfile(user)
    }

    fun onSendingOrder(sendingOrder: Boolean) {
        _sendingOrder.value = sendingOrder
    }
}