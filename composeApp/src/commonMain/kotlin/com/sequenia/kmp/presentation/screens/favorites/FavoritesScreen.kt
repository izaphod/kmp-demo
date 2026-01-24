package com.sequenia.kmp.presentation.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sequenia.kmp.presentation.compose.component.app_bar.TopAppBarComponent
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.title_favorites

@Composable
fun FavoritesScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TopAppBarComponent(
            topAppBarStyle = AppTheme.topAppBarSystem.childTopAppBarStyle,
            title = stringResource(Res.string.title_favorites),
            onBackClick = { navigator.goBack() },
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .background(color = AppTheme.colorSystem.surface)
        )
    }
}