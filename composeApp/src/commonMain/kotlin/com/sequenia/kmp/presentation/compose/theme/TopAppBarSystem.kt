@file:OptIn(ExperimentalMaterial3Api::class)

package com.sequenia.kmp.presentation.compose.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.vectorResource
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.ic_round_arrow_back_24

@Immutable
data class TopAppBarSystem(
    val rootTopAppBarStyle: TopAppBarStyle,
    val childTopAppBarStyle: TopAppBarStyle,
)

@Immutable
data class TopAppBarStyle(
    val heightInDp: Dp,
    val titleStyle: TextStyle,
    val colors: TopAppBarColors,
    val navigationIcon: ImageVector?,
) {
    companion object {
        val Unspecified = TopAppBarStyle(
            heightInDp = Dp.Unspecified,
            titleStyle = TextStyle.Default,
            colors = TopAppBarColors(
                containerColor = Color.Unspecified,
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.Unspecified,
                titleContentColor = Color.Unspecified,
                actionIconContentColor = Color.Unspecified
            ),
            navigationIcon = null
        )
    }
}

val LocalTopAppBarSystem = staticCompositionLocalOf {
    TopAppBarSystem(
        rootTopAppBarStyle = TopAppBarStyle.Unspecified,
        childTopAppBarStyle = TopAppBarStyle.Unspecified,
    )
}

@Composable
fun createTopAppBarSystem(
    colorSystem: ColorSystem,
    typographySystem: TypographySystem
): TopAppBarSystem {
    val heightInDp = 56.dp
    val titleStyle = typographySystem.appBarTitle
    val colors = TopAppBarColors(
        containerColor = colorSystem.appBarContainer,
        scrolledContainerColor = colorSystem.appBarContainer,
        navigationIconContentColor = colorSystem.appBarContent,
        titleContentColor = colorSystem.appBarContent,
        actionIconContentColor = colorSystem.appBarContent
    )

    return TopAppBarSystem(
        rootTopAppBarStyle = TopAppBarStyle(
            heightInDp = heightInDp,
            titleStyle = titleStyle,
            colors = colors,
            navigationIcon = null
        ),
        childTopAppBarStyle = TopAppBarStyle(
            heightInDp = heightInDp,
            titleStyle = titleStyle,
            colors = colors,
            navigationIcon = vectorResource(Res.drawable.ic_round_arrow_back_24)
        ),
    )
}