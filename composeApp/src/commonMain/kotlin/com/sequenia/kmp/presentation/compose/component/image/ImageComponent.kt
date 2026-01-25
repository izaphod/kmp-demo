package com.sequenia.kmp.presentation.compose.component.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sequenia.kmp.presentation.compose.entities.settings.ImageComponentSettings
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ImageComponent(
    url: String?,
    modifier: Modifier = Modifier,
    settings: ImageComponentSettings = ImageComponentSettings(),
    onImageLoading: (() -> Unit)? = null,
    onImageLoadingSuccess: (() -> Unit)? = null,
    onImageLoadingError: (() -> Unit)? = null,
) {
    val contentScale = settings.contentScale
    val alignment = settings.alignment

    SubcomposeAsyncImage(
        model = createImageRequest(url, settings),
        contentDescription = null,
        contentScale = contentScale,
        alignment = alignment,
        loading = {
            onImageLoading?.invoke()
            settings.placeholderDrawableResource?.let { resource ->
                Image(
                    imageVector = vectorResource(resource),
                    contentDescription = null,
                    modifier = modifier,
                    contentScale = contentScale,
                    alignment = alignment
                )
            }
        },
        error = {
            onImageLoadingError?.invoke()
            settings.errorDrawableResource?.let { resource ->
                Image(
                    imageVector = vectorResource(resource),
                    contentDescription = null,
                    modifier = modifier,
                    contentScale = contentScale,
                    alignment = alignment
                )
            }
        },
        success = { state ->
            onImageLoadingSuccess?.invoke()
            Image(
                painter = state.painter,
                contentDescription = null,
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment
            )
        },
        modifier = modifier
    )
}

@Composable
@NonRestartableComposable
private fun createImageRequest(
    url: String?,
    settings: ImageComponentSettings
): ImageRequest {
    val context = LocalPlatformContext.current

    return remember(url, settings) {
        ImageRequest.Builder(context)
            .data(url)
            .crossfade(settings.crossFadeDurationInMilliseconds)
            .build()
    }
}