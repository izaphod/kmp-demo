package com.sequenia.kmp.presentation.compose.component.button

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sequenia.kmp.presentation.compose.theme.FabSystem

@Composable
fun FabComponent(
    onClick: () -> Unit,
    icon: ImageVector,
    style: FabSystem.FabStyle,
    modifier: Modifier = Modifier
) {
    val elevationDp = style.elevationDp

    FloatingActionButton(
        onClick = onClick,
        containerColor = style.containerColor,
        contentColor = style.contentColor,
        shape = style.shape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = elevationDp,
            pressedElevation = elevationDp,
            focusedElevation = elevationDp,
            hoveredElevation = elevationDp
        ),
        modifier = modifier
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}