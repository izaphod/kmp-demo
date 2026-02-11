package com.sequenia.kmp.presentation.compose.component.image

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sequenia.kmp.presentation.compose.entities.settings.ImageComponentSettings
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.ic_placeholder

@Composable
fun MoviePosterComponent(
    posterUrl: String?,
    aspectRatio: Float,
    modifier: Modifier = Modifier
) {
    val settings = remember {
        val errorDrawableResource = Res.drawable.ic_placeholder
        ImageComponentSettings(
            contentScale = ContentScale.FillBounds,
            errorDrawableResource = errorDrawableResource,
            placeholderDrawableResource = errorDrawableResource
        )
    }

    ImageComponent(
        url = posterUrl,
        settings = settings,
        modifier = modifier
            .aspectRatio(ratio = aspectRatio)
            .clip(shape = RoundedCornerShape(size = 4.dp))
    )
}