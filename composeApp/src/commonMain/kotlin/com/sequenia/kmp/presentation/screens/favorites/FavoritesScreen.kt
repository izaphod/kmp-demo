package com.sequenia.kmp.presentation.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sequenia.kmp.presentation.compose.component.app_bar.TopAppBarComponent
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.navigator.Navigator
import com.sequenia.kmp.presentation.navigation.routes.Route
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

        FilledButtonComponent(
            text = "WebView",
            onClick = { navigator.navigate(key = Route.WebViewRoute) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        FilledButtonComponent(
            text = "Еще один экран Избранное",
            onClick = { navigator.navigate(key = Route.FavoritesRoute) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        FilledButtonComponent(
            text = "Настройки с popUpTo в начало",
            onClick = { 
                navigator.navigate(
                    key = Route.SettingsRoute,
                    popUpTo = Route.BottomNavigationRoute
                ) 
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun FilledButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.filledTonalButtonColors().copy(
            containerColor = AppTheme.colorSystem.fabContainerColor,
            contentColor = AppTheme.colorSystem.fabContentColor
        ),
        modifier = modifier
    ) {
        Text(
            text = text
        )
    }
}