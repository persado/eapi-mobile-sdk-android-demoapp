package com.persado.sdkexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.persado.sdkexample.ui.PAppState
import com.persado.sdkexample.ui.confirmation.ConfirmationScreen
import com.persado.sdkexample.ui.landing.LandingScreen
import com.persado.sdkexample.ui.landing.MainViewModel
import com.persado.sdkexample.ui.product.ProductScreen
import com.persado.sdkexample.ui.shoppingcart.ShoppingCartScreen

@Composable
fun NavGraph(
    appState: PAppState
) {
    val navHostController = appState.navController
    NavHost(
        navController = navHostController,
        startDestination = Screen.Landing.route
    ) {
        composable(Screen.Landing.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(Screen.Landing.route)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
            appState.onCartChanged(uiState.cart.size)

            LandingScreen(
                onItemSelected = {
                    navHostController.navigate(Screen.Product.route)
                },
                mainViewModel = mainViewModel
            )
        }
        composable(Screen.Product.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(Screen.Landing.route)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            ProductScreen(
                onContinueShopping = {
                    navHostController.popBackStack()
                },
                onCheckout = {
                    navHostController.navigate(Screen.ShoppingCart.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                mainViewModel = mainViewModel
            )
        }
        composable(Screen.ShoppingCart.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(Screen.Landing.route)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
            appState.onCartChanged(uiState.cart.size)
            appState.onSendingOrder(uiState.sendingOrder)

            ShoppingCartScreen(
                onCta = {
                    navHostController.navigate(Screen.Confirmation.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                mainViewModel = mainViewModel
            )
        }
        composable(Screen.Confirmation.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(Screen.Landing.route)
            }
            val mainViewModel = hiltViewModel<MainViewModel>(parentEntry)

            ConfirmationScreen(
                onBackToShop = {
                    navHostController.popBackStack()
                },
                mainViewModel = mainViewModel
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object Landing : Screen("landing")
    data object Product : Screen("product")
    data object ShoppingCart : Screen("shopping_cart")
    data object Confirmation : Screen("confirmation")
}