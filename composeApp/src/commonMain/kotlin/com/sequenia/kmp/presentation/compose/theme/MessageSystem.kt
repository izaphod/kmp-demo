package com.sequenia.kmp.presentation.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class MessageSystem(
    val error: MessageStyle,
)

@Immutable
data class MessageStyle(
    val contentStyle: TextStyle,
    val actionStyle: TextStyle,
    val containerColor: Color,
    val contentColor: Color,
    val actionContentColor: Color,
    val shape: Shape,
    val minHeight: Dp,
) {
    companion object {
        val Unspecified = MessageStyle(
            contentStyle = TextStyle.Default,
            actionStyle = TextStyle.Default,
            containerColor = Color.Unspecified,
            contentColor = Color.Unspecified,
            actionContentColor = Color.Unspecified,
            shape = RectangleShape,
            minHeight = Dp.Unspecified
        )
    }
}

val LocalMessageSystem = staticCompositionLocalOf {
    MessageSystem(
        error = MessageStyle.Unspecified,
    )
}

@Composable
@ReadOnlyComposable
fun createMessageSystem(
    colorSystem: ColorSystem,
    typographySystem: TypographySystem
): MessageSystem {
    return MessageSystem(
        error = MessageStyle(
            contentStyle = typographySystem.error,
            actionStyle = typographySystem.errorAction,
            containerColor = colorSystem.errorContainer,
            contentColor = colorSystem.errorContent,
            actionContentColor = colorSystem.errorAction,
            shape = RoundedCornerShape(2.dp),
            minHeight = 56.dp
        ),
    )
}