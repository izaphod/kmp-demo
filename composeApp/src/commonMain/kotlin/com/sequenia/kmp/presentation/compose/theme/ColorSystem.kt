package com.sequenia.kmp.presentation.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorSystem(
    val surface: Color,
    val appBarContainer: Color,
    val appBarContent: Color,
    val genreContainer: Color,
    val selectedGenreContainer: Color,
    val moviesHeader: Color,
    val bottomSheetHeader: Color,
    val settingsHeader: Color,
    val movieGenre: Color,
    val movieInListName: Color,
    val movieName: Color,
    val movieDetails: Color,
    val movieRating: Color,
    val movieRatingSource: Color,
    val movieDescription: Color,
    val errorContainer: Color,
    val errorContent: Color,
    val errorAction: Color,
    val circularLoader: Color,
    val checkboxLabel: Color,
    val checkboxCheckedCheckmarkColor: Color,
    val checkboxUncheckedCheckmarkColor: Color,
    val checkboxCheckedBoxColor: Color,
    val checkboxUncheckedBoxColor: Color,
    val checkboxCheckedBorderColor: Color,
    val checkboxUncheckedBorderColor: Color,
    val fabContainerColor: Color,
    val fabContentColor: Color,
)

val LocalColorSystem = staticCompositionLocalOf {
    ColorSystem(
        surface = Color.Unspecified,
        appBarContainer = Color.Unspecified,
        appBarContent = Color.Unspecified,
        genreContainer = Color.Unspecified,
        selectedGenreContainer = Color.Unspecified,
        moviesHeader = Color.Unspecified,
        bottomSheetHeader = Color.Unspecified,
        settingsHeader = Color.Unspecified,
        movieGenre = Color.Unspecified,
        movieInListName = Color.Unspecified,
        movieName = Color.Unspecified,
        movieDetails = Color.Unspecified,
        movieRating = Color.Unspecified,
        movieRatingSource = Color.Unspecified,
        movieDescription = Color.Unspecified,
        errorContainer = Color.Unspecified,
        errorContent = Color.Unspecified,
        errorAction = Color.Unspecified,
        circularLoader = Color.Unspecified,
        checkboxLabel = Color.Unspecified,
        checkboxCheckedCheckmarkColor = Color.Unspecified,
        checkboxUncheckedCheckmarkColor = Color.Unspecified,
        checkboxCheckedBoxColor = Color.Unspecified,
        checkboxUncheckedBoxColor = Color.Unspecified,
        checkboxCheckedBorderColor = Color.Unspecified,
        checkboxUncheckedBorderColor = Color.Unspecified,
        fabContainerColor = Color.Unspecified,
        fabContentColor = Color.Unspecified,
    )
}

@Composable
@ReadOnlyComposable
fun createColorSystem(): ColorSystem {
    val whiteColor = Color(color = 0xFFFFFFFF)
    val blueColor = Color(color = 0xFF0E3165)
    val yellowColor = Color(color = 0xFFFFC967)
    val blackColor = Color(color = 0xFF000000)

    return ColorSystem(
        surface = whiteColor,
        appBarContainer = blueColor,
        genreContainer = whiteColor,
        selectedGenreContainer = yellowColor,
        appBarContent = whiteColor,
        moviesHeader = blackColor,
        bottomSheetHeader = blackColor,
        settingsHeader = blackColor,
        movieGenre = blackColor,
        movieInListName = blackColor,
        movieName = blackColor,
        movieDetails = Color(color = 0xFF4B4B4B),
        movieRating = blueColor,
        movieRatingSource = blueColor,
        movieDescription = blackColor,
        errorContainer = Color(color = 0xFF232323),
        errorContent = whiteColor,
        errorAction = yellowColor,
        circularLoader = yellowColor,
        checkboxLabel = blackColor,
        checkboxCheckedCheckmarkColor = whiteColor,
        checkboxUncheckedCheckmarkColor = Color.Transparent,
        checkboxCheckedBoxColor = blackColor,
        checkboxUncheckedBoxColor = whiteColor,
        checkboxCheckedBorderColor = blueColor,
        checkboxUncheckedBorderColor = blackColor,
        fabContainerColor = yellowColor,
        fabContentColor = blackColor
    )
}