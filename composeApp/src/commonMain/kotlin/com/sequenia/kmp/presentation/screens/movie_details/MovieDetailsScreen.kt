package com.sequenia.kmp.presentation.screens.movie_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.presentation.compose.component.app_bar.TopAppBarComponent
import com.sequenia.kmp.presentation.compose.component.image.MoviePosterComponent
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.movie_rating_source
import sequeniakmp.composeapp.generated.resources.movie_year
import kotlin.math.round

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val horizontalSides = WindowInsetsSides.Horizontal
    val insetsCutoutHorizontal = WindowInsets.displayCutout.only(horizontalSides)
    val insetsNavBarsHorizontal = WindowInsets.navigationBars.only(horizontalSides)

    Column(modifier) {
        TopAppBarComponent(
            topAppBarStyle = AppTheme.topAppBarSystem.childTopAppBarStyle,
            title = movie.name,
            onBackClick = { navigator.goBack() },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .windowInsetsPadding(insets = insetsCutoutHorizontal)
                .windowInsetsPadding(insets = insetsNavBarsHorizontal)
        ) {
            MoviePosterComponent(
                posterUrl = movie.posterUrl,
                aspectRatio = 132 / 201F,
                modifier = Modifier.padding(horizontal = 98.dp, vertical = 24.dp)
            )

            DetailsComponent(
                movie = movie,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(height = 14.dp))

            Text(
                text = movie.description.orEmpty(),
                style = AppTheme.typographySystem.movieDescription,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun DetailsComponent(movie: Movie, modifier: Modifier = Modifier) {
    val details = formatMovieDetails(movie)
    val rating = movie.rating ?: -1f

    Column(modifier = modifier) {
        Text(
            text = movie.localizedName.orEmpty(),
            style = AppTheme.typographySystem.movieName
        )

        if (details.isNotBlank()) {
            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                text = details,
                style = AppTheme.typographySystem.movieDetails
            )
        }

        if (rating >= 0) {
            Spacer(modifier = Modifier.height(height = 10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = formatMovieRating(rating),
                    style = AppTheme.typographySystem.movieRating
                )

                Text(
                    text = stringResource(Res.string.movie_rating_source),
                    style = AppTheme.typographySystem.movieRatingSource,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun formatMovieDetails(movie: Movie): String {
    val genres = movie.genres?.joinToString()
    val year = movie.year?.toString()
    val formattedYear = when {
        year.isNullOrBlank() -> null
        else -> stringResource(Res.string.movie_year, year)
    }

    return listOfNotNull(genres, formattedYear)
        .filter { it.isNotBlank() }
        .joinToString()
}

private fun formatMovieRating(rating: Float): String {
    return (round(rating * 10) / 10).toString()
}