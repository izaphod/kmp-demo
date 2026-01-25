package com.sequenia.kmp.presentation.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.kmp.domain.entities.movie.Movie
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.domain.models.movies.MoviesModel
import com.sequenia.kmp.domain.models.settings.SettingsModel
import com.sequenia.kmp.presentation.entities.screen_data.movies.MoviesScreenData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.sequenia.kmp.domain.results.ExecutionResult
import com.sequenia.kmp.presentation.screens.movies.MoviesScreenState
import com.sequenia.kmp.presentation.screens.movies.MoviesScreenEvent as ScreenEvent
import com.sequenia.kmp.presentation.screens.movies.MoviesScreenState as ScreenState

class MoviesViewModel(
    private val moviesModel: MoviesModel,
    private val settingsModel: SettingsModel,
) : ViewModel() {

    val screenStateFlow get() = mutableScreenState.asStateFlow()
    val screenEventFlow get() = screenEventChannel.receiveAsFlow()

    private val mutableScreenState = MutableStateFlow<ScreenState>(ScreenState.InitialState)
    private val screenEventChannel = Channel<ScreenEvent>(Channel.UNLIMITED)

    private var movies = emptyList<Movie>()

    private var selectedGenres = mutableSetOf<String>()
    private var screenData = MoviesScreenData()

    init {
        changeScreenStateToLoadingState()
        viewModelScope.launch {
            launch { subscribeToGenresSelectionMode() }
            launch { loadMovies() }
        }
    }

    fun onRepeatClick() {
        changeScreenStateToLoadingState()
        viewModelScope.launch { loadMovies() }
    }

    fun onMovieClick(id: Long) {
        viewModelScope.launch { sendShowMovieEvent(id) }
    }

    fun onGenreClick(genre: String) {
        handleGenre(genre)
        changeScreenStateToSuccessState()
    }

    fun onFavoritesClick() {
        viewModelScope.launch { sendShowFavoritesEvent() }
    }

    private fun handleGenre(genre: String) {
        var needReset = false

        when (screenData.genreSelectionMode) {
            GenresSelectionMode.MULTIPLE -> {}
            GenresSelectionMode.SINGLE -> {
                needReset = selectedGenres.contains(genre)
                selectedGenres.clear()
            }
        }

        if (needReset.not()) {
            if (selectedGenres.add(genre).not()) selectedGenres.remove(genre)
        }

        screenData = screenData.copy(selectedGenres = selectedGenres.toSet())
        updateScreenDataWithFilteredMovies()
    }

    private suspend fun subscribeToGenresSelectionMode() {
        settingsModel.subscribeToGenresSelectionMode()
            .distinctUntilChanged()
            .collect { genresSelectionMode -> handleGenresSelectionMode(genresSelectionMode) }
    }

    private fun handleGenresSelectionMode(genresSelectionMode: GenresSelectionMode) {
        val selectedGenres = when (genresSelectionMode) {
            GenresSelectionMode.MULTIPLE -> {
                this.selectedGenres.toSet()
            }

            GenresSelectionMode.SINGLE -> {
                this.selectedGenres
                    .toList()
                    .sorted()
                    .take(1)
                    .toSet()
            }
        }

        this.selectedGenres.clear()
        this.selectedGenres.addAll(selectedGenres)
        screenData = screenData.copy(
            genreSelectionMode = genresSelectionMode,
            selectedGenres = selectedGenres
        )
        updateScreenDataWithFilteredMovies()
        updateCurrentStateWithScreenData()
    }

    private suspend fun loadMovies() {
        when (val result = moviesModel.loadMovies()) {
            is ExecutionResult.Success -> {
                handleLoadedMovies(result.data)
                changeScreenStateToSuccessState()
            }
            is ExecutionResult.Error -> {
                changeScreenStateToErrorState(result)
            }
        }
    }

    private suspend fun handleLoadedMovies(loadedMovies: List<Movie>) {
        moviesModel.saveMovies(loadedMovies)

        val genres = loadedMovies
            .mapNotNull { movie -> movie.genres }
            .flatten()
            .filter { genre -> genre.isNotBlank() }
            .distinct()
            .sorted()
            .toList()

        movies = loadedMovies
        screenData = screenData.copy(
            movies = loadedMovies,
            genres = genres,
        )
    }

    private fun updateScreenDataWithFilteredMovies() {
        val filteredMovies = if (selectedGenres.isEmpty()) {
            movies
        } else {
            movies.filter { movie ->
                movie.genres.orEmpty().any { genre -> genre in selectedGenres }
            }
        }
        screenData = screenData.copy(movies = filteredMovies)
    }

    private fun updateCurrentStateWithScreenData() {
        when (mutableScreenState.value) {
            is MoviesScreenState.ErrorState -> {}
            is MoviesScreenState.InitialState -> {}
            is MoviesScreenState.LoadingState -> {}
            is MoviesScreenState.SuccessState -> changeScreenStateToSuccessState()
        }
    }

    private fun changeScreenStateToLoadingState() {
        mutableScreenState.value = ScreenState.LoadingState
    }

    private fun changeScreenStateToSuccessState() {
        mutableScreenState.value = ScreenState.SuccessState(screenData)
    }

    private fun changeScreenStateToErrorState(errorResult: ExecutionResult.Error) {
        mutableScreenState.value = ScreenState.ErrorState(errorResult)
    }

    private suspend fun sendShowMovieEvent(movieId: Long) {
        screenEventChannel.send(ScreenEvent.ShowMovieEvent(movieId))
    }

    private suspend fun sendShowFavoritesEvent() {
        screenEventChannel.send(ScreenEvent.ShowFavoritesEvent)
    }
}