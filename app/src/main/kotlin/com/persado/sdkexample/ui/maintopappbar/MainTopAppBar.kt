package com.persado.sdkexample.ui.maintopappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.persado.sdkexample.R
import com.persado.sdkexample.ui.navigation.Screen
import com.persado.sdkexample.ui.theme.PTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navController: NavHostController,
    title: String,
    hideShoppingCartIcon: Boolean = false,
    itemsCount: Int = 0,
    backEnabled: Boolean = true,
    onShowProfile: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior
) {
    MediumTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            if (hideShoppingCartIcon) {
                ShoppingCartIcon(items = itemsCount) {
                    navController.navigate(Screen.ShoppingCart.route)
                }
            }
        },
        navigationIcon = {
            IconButton(
                enabled = backEnabled,
                onClick = {
                    if (navController.currentDestination?.route == Screen.Landing.route) {
                        onShowProfile()
                    } else {
                        navController.popBackStack()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (navController.currentDestination?.route == Screen.Landing.route)
                            R.drawable.person
                        else R.drawable.arrow_back
                    ),
                    contentDescription = stringResource(R.string.button_back)
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun ShoppingCartIcon(items: Int, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = stringResource(R.string.title_shopping_cart),
            )
        }
        if (items > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 8.dp, y = (-8).dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = items.toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewShoppingCartIcon() {
    PTheme {
        ShoppingCartIcon(items = 2) {
        }
    }
}