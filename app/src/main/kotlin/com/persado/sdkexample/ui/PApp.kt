package com.persado.sdkexample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.persado.sdkexample.data.repository.DataRepository
import com.persado.sdkexample.network.PersadoManager
import com.persado.sdkexample.ui.maintopappbar.MainTopAppBar
import com.persado.sdkexample.ui.navigation.NavGraph
import com.persado.sdkexample.ui.navigation.TopLevelDestination
import com.persado.sdkexample.ui.profile.ProfileDialog
import com.persado.sdkexample.ui.theme.PTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PApp(
    dataRepository: DataRepository,
    persadoManager: PersadoManager,
    appState: PAppState = rememberPAppState(
        dataRepository = dataRepository,
        persadoManager = persadoManager
    )
) {
    PTheme {
        val itemsCount by appState.itemsCount.collectAsStateWithLifecycle()
        val cartContent by appState.cartContent.collectAsStateWithLifecycle()
        val users by appState.users.collectAsStateWithLifecycle()
        val sendingOrder by appState.sendingOrder.collectAsStateWithLifecycle()
        var showProfileDialog by remember { mutableStateOf(false) }

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

        LaunchedEffect(key1 = users) {
            if (users.isNotEmpty()) {
                appState.onUserChange(users[0])
            }
        }

        if (showProfileDialog && appState.user != null) {
            ProfileDialog(
                selected = appState.user!!,
                onSelectProfile = {
                    appState.onUserChange(it)
                    showProfileDialog = false
                }
            )
        }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            snackbarHost = { }
        ) { innerPadding ->

            Column(modifier = Modifier.padding(innerPadding)) {

                val destination = appState.currentTopLevelDestination

                if (destination != null) {
                    val title = if (destination == TopLevelDestination.SHOPPING_CART) {
                        cartContent.headline
                    } else {
                        stringResource(id = destination.titleTextId)
                    }
                    MainTopAppBar(
                        navController = appState.navController,
                        title = title,
                        hideShoppingCartIcon = !appState.showShoppingCart,
                        itemsCount = itemsCount,
                        backEnabled = !sendingOrder,
                        onShowProfile = {
                            showProfileDialog = true
                        },
                        scrollBehavior = scrollBehavior
                    )
                }

                NavGraph(
                    appState = appState
                )
            }
        }
    }
}