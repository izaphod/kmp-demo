@file:OptIn(ExperimentalMaterial3Api::class)

package com.sequenia.kmp.presentation.compose.component.app_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.sequenia.kmp.presentation.compose.theme.TopAppBarStyle

@Composable
fun TopAppBarComponent(
    topAppBarStyle: TopAppBarStyle,
    modifier: Modifier = Modifier,
    title: String? = null,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val navigationIcon = topAppBarStyle.navigationIcon

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title.orEmpty(),
                style = topAppBarStyle.titleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        },
        windowInsets = windowInsets,
        expandedHeight = topAppBarStyle.heightInDp,
        colors = topAppBarStyle.colors,
        navigationIcon = {
            if (navigationIcon != null) {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentSize provides Dp.Unspecified
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(painter = navigationIcon, contentDescription = null)
                    }
                }
            }
        },
        actions = actions,
        modifier = modifier,
    )
}