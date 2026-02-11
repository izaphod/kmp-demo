package com.sequenia.kmp.presentation.compose.entities.settings

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource

@Immutable
data class ImageComponentSettings(
    val alignment: Alignment = Alignment.Center,
    val contentScale: ContentScale = ContentScale.Fit,
    val crossFadeDurationInMilliseconds: Int = 100,
    val placeholderDrawableResource: DrawableResource? = null,
    val errorDrawableResource: DrawableResource? = null
)