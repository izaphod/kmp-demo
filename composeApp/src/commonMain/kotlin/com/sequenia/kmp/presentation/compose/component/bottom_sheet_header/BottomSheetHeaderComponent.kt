package com.sequenia.kmp.presentation.compose.component.bottom_sheet_header

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sequenia.kmp.presentation.compose.theme.AppTheme

@Composable
fun BottomSheetHeaderComponent(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = AppTheme.typographySystem.bottomSheetHeader,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .padding(all = 16.dp)
    )
}