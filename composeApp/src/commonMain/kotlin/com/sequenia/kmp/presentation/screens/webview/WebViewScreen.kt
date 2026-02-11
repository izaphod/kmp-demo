package com.sequenia.kmp.presentation.screens.webview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberSaveableWebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.sequenia.kmp.presentation.compose.component.app_bar.TopAppBarComponent
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.navigator.Navigator

@Composable
fun WebViewScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val url = "https://kevinnzou.github.io/compose-webview-multiplatform/"
    val webViewState = rememberSaveableWebViewState(url = url)
    val webViewNavigator = rememberWebViewNavigator()

    LaunchedEffect(key1 = url) {
        webViewNavigator.loadUrl(url = url)
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBarComponent(
            topAppBarStyle = AppTheme.topAppBarSystem.childTopAppBarStyle,
            title = "WebView",
            onBackClick = {
                if (webViewNavigator.canGoBack) {
                    webViewNavigator.navigateBack()
                } else {
                    navigator.goBack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f)
        ) {
            WebViewWithLoading(
                webViewState = webViewState,
                webViewNavigator = webViewNavigator
            )
        }
    }
}

@Composable
private fun BoxScope.WebViewWithLoading(
    webViewState: WebViewState,
    webViewNavigator: WebViewNavigator
) {
    WebView(
        state = webViewState,
        navigator = webViewNavigator,
        modifier = Modifier.fillMaxSize()
    )

    AnimatedVisibility(visible = webViewState.isLoading) {
        LinearProgressIndicator(
            color = AppTheme.colorSystem.circularLoader,
            trackColor = AppTheme.colorSystem.appBarContainer,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )
    }
}