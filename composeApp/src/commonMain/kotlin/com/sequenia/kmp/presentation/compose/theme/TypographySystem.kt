package com.sequenia.kmp.presentation.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class TypographySystem(
    val appBarTitle: TextStyle,
    val moviesHeader: TextStyle,
    val bottomSheetHeader: TextStyle,
    val settingsHeader: TextStyle,
    val genre: TextStyle,
    val movieInListName: TextStyle,
    val movieName: TextStyle,
    val movieDetails: TextStyle,
    val movieRating: TextStyle,
    val movieRatingSource: TextStyle,
    val movieDescription: TextStyle,
    val errorAction: TextStyle,
    val error: TextStyle,
    val commonCheckboxLabel: TextStyle,
)

val LocalTypographySystem = staticCompositionLocalOf {
    TypographySystem(
        appBarTitle = TextStyle.Default,
        moviesHeader = TextStyle.Default,
        bottomSheetHeader = TextStyle.Default,
        settingsHeader = TextStyle.Default,
        genre = TextStyle.Default,
        movieInListName = TextStyle.Default,
        movieName = TextStyle.Default,
        movieDetails = TextStyle.Default,
        movieRating = TextStyle.Default,
        movieRatingSource = TextStyle.Default,
        movieDescription = TextStyle.Default,
        errorAction = TextStyle.Default,
        error = TextStyle.Default,
        commonCheckboxLabel = TextStyle.Default,
    )
}

@Composable
@ReadOnlyComposable
fun createTypographySystem(colorSystem: ColorSystem): TypographySystem {
    val robotoFontFamily = FontFamily.SansSerif
    val header = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.1.sp
    )
    return TypographySystem(
        appBarTitle = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            color = colorSystem.appBarContent,
            letterSpacing = 0.15.sp
        ),
        moviesHeader = header.copy(color = colorSystem.moviesHeader),
        bottomSheetHeader = header.copy(color = colorSystem.bottomSheetHeader),
        settingsHeader = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = colorSystem.settingsHeader,
            letterSpacing = 0.1.sp
        ),
        genre = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = colorSystem.movieGenre,
            letterSpacing = 0.1.sp
        ),
        movieInListName = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = colorSystem.movieInListName,
            letterSpacing = 0.1.sp
        ),
        movieName = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            lineHeight = 32.sp,
            color = colorSystem.movieName,
            letterSpacing = 0.1.sp
        ),
        movieDetails = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = colorSystem.movieDetails,
            letterSpacing = 0.1.sp
        ),
        movieRating = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            color = colorSystem.movieRating,
            letterSpacing = 0.1.sp
        ),
        movieRatingSource = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = colorSystem.movieRatingSource,
            letterSpacing = 0.1.sp
        ),
        movieDescription = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = colorSystem.movieDescription,
            letterSpacing = 0.1.sp
        ),
        errorAction = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            color = colorSystem.errorAction
        ),
        error = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            color = colorSystem.errorContent
        ),
        commonCheckboxLabel = TextStyle(
            fontFamily = robotoFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = colorSystem.movieDescription,
            letterSpacing = 0.1.sp
        )
    )
}