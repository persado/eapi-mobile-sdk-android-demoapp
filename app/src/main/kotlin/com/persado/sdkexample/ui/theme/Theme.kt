package com.persado.sdkexample.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = PrimaryBlack,
    onPrimary = PrimaryWhite,
    primaryContainer = PrimaryWhite,
    onPrimaryContainer = PrimaryBlack,
    secondaryContainer = PrimaryWhite,
    onSecondaryContainer = PrimaryWhite,
    tertiaryContainer = PrimaryWhite,
    onTertiaryContainer = PrimaryWhite,
    surfaceVariant = PrimaryWhite,
    onSurfaceVariant = PrimaryBlack,
    surface = PrimaryWhite,
    onSurface = PrimaryBlack,
    background = PrimaryWhite,
    onBackground = PrimaryBlack,
    errorContainer = PrimaryWhite,
    onErrorContainer = ErrorRed,
)

@Composable
fun PTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = Typography,
        content = content
    )
}