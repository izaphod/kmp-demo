@file:OptIn(ExperimentalMaterial3Api::class)

package com.sequenia.kmp.presentation.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.results.ExecutionResult
import com.sequenia.kmp.presentation.compose.component.app_bar.TopAppBarComponent
import com.sequenia.kmp.presentation.compose.component.image.MoviePosterComponent
import com.sequenia.kmp.presentation.compose.entities.constants.ContentTypes
import com.sequenia.kmp.presentation.compose.extension.defineMessage
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.entities.screen_data.movies.MoviesScreenData
import com.sequenia.kmp.presentation.navigation.navigator.CommonNavigator
import com.sequenia.kmp.presentation.navigation.routes.Route
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import ru.sequenia.test.ui.compose.component.message.ErrorMessageComponent
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.button_repeat
import sequeniakmp.composeapp.generated.resources.ic_outline_favorite_24
import sequeniakmp.composeapp.generated.resources.title_genres
import sequeniakmp.composeapp.generated.resources.title_movies

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    commonNavigator: CommonNavigator,
    modifier: Modifier = Modifier,
) {
    val screenState = viewModel.screenStateFlow.collectAsStateWithLifecycle().value
    val data: MoviesScreenData
    val isLoading: Boolean
    val errorResult: ExecutionResult.Error?

    when (screenState) {
        is MoviesScreenState.InitialState -> return
        is MoviesScreenState.LoadingState -> {
            data = MoviesScreenData()
            isLoading = true
            errorResult = null
        }
        is MoviesScreenState.SuccessState -> {
            data = screenState.data
            isLoading = false
            errorResult = null
        }
        is MoviesScreenState.ErrorState -> {
            data = MoviesScreenData()
            isLoading = false
            errorResult = screenState.errorResult
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val hasError = errorResult != null
    val errorMessage = errorResult?.defineMessage().orEmpty()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    LifecycleStartEffect(Unit) {
        val eventSubscription = viewModel.screenEventFlow.onEach { event ->
            when (event) {
                is MoviesScreenEvent.ShowMovieEvent -> {
                    val bottomNavNavigator = commonNavigator.bottomNavNavigator
                    bottomNavNavigator.navigate(Route.MovieDetailsRoute(event.movieId))
                }

                is MoviesScreenEvent.ShowFavoritesEvent -> {
                    commonNavigator.mainNavigator.navigate(Route.FavoritesRoute)
                }
            }
        }.launchIn(coroutineScope)

        onStopOrDispose { eventSubscription.cancel() }
    }

    Box(modifier) {
        val horizontalSides = WindowInsetsSides.Horizontal
        val insetsCutoutHorizontal = WindowInsets.displayCutout.only(horizontalSides)
        val insetsNavBarsHorizontal = WindowInsets.navigationBars.only(horizontalSides)

        Column {
            TopAppBarComponent(
                topAppBarStyle = AppTheme.topAppBarSystem.rootTopAppBarStyle,
                title = stringResource(Res.string.title_movies),
                actions = {
                    CompositionLocalProvider(
                        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
                    ) {
                        IconButton(
                            onClick = viewModel::onFavoritesClick
                        ) {
                            val icon = vectorResource(Res.drawable.ic_outline_favorite_24)
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            MoviesComponent(
                onMovieClick = viewModel::onMovieClick,
                onGenreClick = viewModel::onGenreClick,
                data = data,
                showLoading = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = AppTheme.colorSystem.circularLoader
                        )
                    }

                    isLoading
                },
                showError = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .padding(all = 8.dp)
                    ) {
                        ErrorMessageComponent(
                            message = errorMessage,
                            action = stringResource(Res.string.button_repeat),
                            onActionClick = viewModel::onRepeatClick
                        )
                    }

                    hasError
                },
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(insets = insetsCutoutHorizontal)
                    .windowInsetsPadding(insets = insetsNavBarsHorizontal)
            )
        }
    }
}

@Composable
private fun MoviesComponent(
    onMovieClick: (id: Long) -> Unit,
    onGenreClick: (genre: String) -> Unit,
    data: MoviesScreenData,
    showLoading: @Composable (BoxScope.() -> Boolean),
    showError: @Composable (BoxScope.() -> Boolean),
    modifier: Modifier = Modifier
) {
    val genresTitle = stringResource(Res.string.title_genres)
    val moviesTitle = stringResource(Res.string.title_movies)
    val maxWidthItemSpan = GridItemSpan(currentLineSpan = 2)
    val fullSpan: LazyGridItemSpanScope.() -> GridItemSpan = { maxWidthItemSpan }
    val selectedGenres = data.selectedGenres

    Box(modifier = modifier) {
        if (showLoading()) return@Box

        if (showError()) return@Box

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item(span = fullSpan, key = genresTitle, contentType = ContentTypes.HEADER) {
                HeaderComponent(title = genresTitle, modifier = Modifier.fillMaxWidth())
            }

            items(
                items = data.genres,
                key = { genre -> genre },
                contentType = { ContentTypes.GENRE },
                span = { maxWidthItemSpan }
            ) { genre ->
                GenreComponent(
                    onGenreClick = onGenreClick,
                    genre = genre,
                    isSelected = selectedGenres.contains(genre),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item(span = fullSpan) {
                Spacer(modifier = Modifier.height(height = 16.dp))
            }

            item(span = fullSpan, key = moviesTitle, contentType = ContentTypes.HEADER) {
                HeaderComponent(title = moviesTitle, modifier = Modifier.fillMaxWidth())
            }

            item(span = fullSpan) {
                Spacer(modifier = Modifier.height(height = 8.dp))
            }

            items(
                items = data.movies,
                key = { movie -> movie.id?.toString() ?: movie.name.orEmpty() },
                contentType = { ContentTypes.MOVIE }
            ) { movie ->
                Column(Modifier.animateItem()) {
                    MoviePreviewComponent(
                        onMovieClick = onMovieClick,
                        movie = movie,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(height = 16.dp))
                }
            }
        }
    }
}

@Composable
private fun HeaderComponent(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = AppTheme.typographySystem.moviesHeader,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun GenreComponent(
    onGenreClick: (genre: String) -> Unit,
    genre: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val colorSystem = AppTheme.colorSystem
    val color = if (isSelected) colorSystem.selectedGenreContainer else colorSystem.genreContainer
    val horizontalPaddingDp = 16.dp
    val offsetPx = LocalDensity.current.run { horizontalPaddingDp.roundToPx() }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Box(
            modifier = modifier
                .clickable { onGenreClick(genre) }
                .layout { measurable, constraints ->
                    val looseConstraints = constraints.offset(
                        horizontal = offsetPx * 2,
                        vertical = 0
                    )
                    val placeable = measurable.measure(looseConstraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(x = 0, y = 0)
                    }
                }
                .background(color)
                .padding(horizontal = horizontalPaddingDp, vertical = 10.dp)
        ) {
            Text(
                text = genre.replaceRange(
                    startIndex = 0,
                    endIndex = 1,
                    replacement = genre.first().uppercase()
                ),
                style = AppTheme.typographySystem.genre,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MoviePreviewComponent(
    onMovieClick: (id: Long) -> Unit,
    movie: Movie,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            modifier = modifier.clickable { onMovieClick(movie.id ?: return@clickable) }
        ) {
            MoviePosterComponent(
                posterUrl = movie.posterUrl,
                aspectRatio = 160 / 222F
            )

            Text(
                text = movie.localizedName.orEmpty(),
                style = AppTheme.typographySystem.movieInListName,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}