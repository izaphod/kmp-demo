package com.sequenia.kmp.presentation.compose.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ButtonSystem(
    val checkboxSystem: CheckboxSystem,
    val fabSystem: FabSystem,
)

@Immutable
data class CheckboxSystem(
    val commonCheckboxStyle: CheckboxStyle,
) {

    @Immutable
    data class CheckboxStyle(
        val checkedCheckmarkColor: Color,
        val uncheckedCheckmarkColor: Color,
        val checkedBoxColor: Color,
        val uncheckedBoxColor: Color,
        val checkedBorderColor: Color,
        val uncheckedBorderColor: Color,
        val labelStyle: TextStyle,
    ) {
        companion object {
            val Unspecified = CheckboxStyle(
                checkedCheckmarkColor = Color.Unspecified,
                uncheckedCheckmarkColor = Color.Unspecified,
                checkedBoxColor = Color.Unspecified,
                uncheckedBoxColor = Color.Unspecified,
                checkedBorderColor = Color.Unspecified,
                uncheckedBorderColor = Color.Unspecified,
                labelStyle = TextStyle.Default,
            )
        }
    }
}

data class FabSystem(
    val commonFabStyle: FabStyle,
) {

    data class FabStyle(
        val containerColor: Color,
        val contentColor: Color,
        val shape: Shape,
        val elevationDp: Dp
    ) {
        companion object {
            val Unspecified = FabStyle(
                containerColor = Color.Unspecified,
                contentColor = Color.Unspecified,
                shape = CircleShape,
                elevationDp = Dp.Unspecified
            )
        }
    }
}

val LocalButtonSystem = staticCompositionLocalOf {
    ButtonSystem(
        checkboxSystem = CheckboxSystem(
            commonCheckboxStyle = CheckboxSystem.CheckboxStyle.Unspecified,
        ),
        fabSystem = FabSystem(
            commonFabStyle = FabSystem.FabStyle.Unspecified,
        )
    )
}

@Composable
@ReadOnlyComposable
fun createButtonSystem(
    colorSystem: ColorSystem,
    typographySystem: TypographySystem
): ButtonSystem {
    return ButtonSystem(
        checkboxSystem = createCheckboxSystem(colorSystem, typographySystem),
        fabSystem = createFabSystem(colorSystem)
    )
}

@Composable
@ReadOnlyComposable
private fun createCheckboxSystem(
    colorSystem: ColorSystem,
    typographySystem: TypographySystem
): CheckboxSystem {
    return CheckboxSystem(
        commonCheckboxStyle = CheckboxSystem.CheckboxStyle(
            checkedCheckmarkColor = colorSystem.checkboxCheckedCheckmarkColor,
            uncheckedCheckmarkColor = colorSystem.checkboxUncheckedCheckmarkColor,
            checkedBoxColor = colorSystem.checkboxCheckedBoxColor,
            uncheckedBoxColor = colorSystem.checkboxUncheckedBoxColor,
            checkedBorderColor = colorSystem.checkboxCheckedBorderColor,
            uncheckedBorderColor = colorSystem.checkboxUncheckedBorderColor,
            labelStyle = typographySystem.commonCheckboxLabel
        )
    )
}

@Composable
@ReadOnlyComposable
private fun createFabSystem(colorSystem: ColorSystem): FabSystem = FabSystem(
    commonFabStyle = FabSystem.FabStyle(
        containerColor = colorSystem.fabContainerColor,
        contentColor = colorSystem.fabContentColor,
        shape = CircleShape,
        elevationDp = 4.dp
    )
)