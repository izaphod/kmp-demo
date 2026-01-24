package com.sequenia.kmp.presentation.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colorSystem = createColorSystem()
    val typographySystem = createTypographySystem(colorSystem)
    val topAppBarSystem = createTopAppBarSystem(colorSystem, typographySystem)
    val messageSystem = createMessageSystem(colorSystem, typographySystem)
    val buttonSystem = createButtonSystem(colorSystem, typographySystem)

    MaterialTheme(colorScheme = lightColorScheme(background = colorSystem.surface)) {
        CompositionLocalProvider(
            LocalColorSystem provides colorSystem,
            LocalTypographySystem provides typographySystem,
            LocalTopAppBarSystem provides topAppBarSystem,
            LocalMessageSystem provides messageSystem,
            LocalButtonSystem provides buttonSystem,
            content = content
        )
    }
}

object AppTheme {

    val colorSystem: ColorSystem
        @Composable
        @ReadOnlyComposable
        get() = LocalColorSystem.current

    val typographySystem: TypographySystem
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographySystem.current

    val topAppBarSystem: TopAppBarSystem
        @Composable
        @ReadOnlyComposable
        get() = LocalTopAppBarSystem.current

    val messageSystem: MessageSystem
        @Composable
        @ReadOnlyComposable
        get() = LocalMessageSystem.current

    val buttonSystem: ButtonSystem
        @Composable
        @ReadOnlyComposable
        get() = LocalButtonSystem.current
}