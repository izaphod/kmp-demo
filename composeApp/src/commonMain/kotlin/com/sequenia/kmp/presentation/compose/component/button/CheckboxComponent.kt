package com.sequenia.kmp.presentation.compose.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sequenia.kmp.presentation.compose.theme.CheckboxSystem

@Composable
fun CheckboxComponent(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    style: CheckboxSystem.CheckboxStyle,
    modifier: Modifier = Modifier,
    label: String? = null,
    isEnabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val checkedState = remember { mutableStateOf(isChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        modifier = modifier
            .clickable(
                enabled = isEnabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    checkedState.value = checkedState.value.not()
                    onCheckedChange(checkedState.value)
                }
            )
    ) {
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentSize provides Dp.Unspecified
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                enabled = isEnabled,
                colors = createCheckboxColors(style),
                interactionSource = interactionSource
            )
        }

        if (label != null) {
            Text(
                text = label,
                style = style.labelStyle,
                modifier = Modifier.weight(weight = 1F)
            )
        }
    }
}

private fun createCheckboxColors(
    style: CheckboxSystem.CheckboxStyle
): CheckboxColors {
    val disabledCheckedBoxColor = style.checkedBoxColor.copy(alpha = 0.8F)
    val disabledUncheckedBorderColor = style.checkedBorderColor.copy(alpha = 0.8F)

    return CheckboxColors(
        checkedCheckmarkColor = style.checkedCheckmarkColor,
        uncheckedCheckmarkColor = style.uncheckedCheckmarkColor,
        checkedBoxColor = style.checkedBoxColor,
        uncheckedBoxColor = style.uncheckedBoxColor,
        disabledCheckedBoxColor = disabledCheckedBoxColor,
        disabledUncheckedBoxColor = style.uncheckedBoxColor,
        disabledIndeterminateBoxColor = style.uncheckedBoxColor,
        checkedBorderColor = style.checkedBorderColor,
        uncheckedBorderColor = style.uncheckedBorderColor,
        disabledBorderColor = disabledCheckedBoxColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
        disabledIndeterminateBorderColor = disabledUncheckedBorderColor
    )
}