package com.sequenia.kmp.presentation.extensions

import androidx.compose.runtime.Composable
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import org.jetbrains.compose.resources.stringResource
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.label_multiple_selection_mode
import sequeniakmp.composeapp.generated.resources.label_single_selection_mode

@Composable
fun GenresSelectionMode.defineLabel(): String {
    return when (this) {
        GenresSelectionMode.MULTIPLE -> stringResource(Res.string.label_multiple_selection_mode)
        GenresSelectionMode.SINGLE -> stringResource(Res.string.label_single_selection_mode)
    }
}