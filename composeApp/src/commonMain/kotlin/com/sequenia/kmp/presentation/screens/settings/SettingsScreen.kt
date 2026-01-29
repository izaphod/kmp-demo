package com.sequenia.kmp.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.getPlatform
import com.sequenia.kmp.presentation.compose.component.app_bar.TopAppBarComponent
import com.sequenia.kmp.presentation.compose.component.button.CheckboxComponent
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.extensions.defineLabel
import com.sequenia.kmp.presentation.navigation.navigator.Navigator
import org.jetbrains.compose.resources.stringResource
import ru.sequenia.test.ui.screens.settings.SettingsViewModel
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.platform_name
import sequeniakmp.composeapp.generated.resources.title_genres_selection_settings
import sequeniakmp.composeapp.generated.resources.title_settings

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
) {
    val state = viewModel.screenStateFlow.collectAsStateWithLifecycle()
    val initialGenresSelectionMode = when (val screenState = state.value) {
        is SettingsScreenState.InitialState -> return
        is SettingsScreenState.SuccessState -> screenState.sortSelectionMode
    }

    val genresSelectionModeState = rememberSaveable {
        mutableStateOf(initialGenresSelectionMode)
    }

    val horizontalSides = WindowInsetsSides.Horizontal
    val insetsCutoutHorizontal = WindowInsets.displayCutout.only(horizontalSides)
    val insetsNavBarsHorizontal = WindowInsets.navigationBars.only(horizontalSides)

    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        TopAppBarComponent(
            topAppBarStyle = if (isFullScreen) {
                AppTheme.topAppBarSystem.childTopAppBarStyle
            } else {
                AppTheme.topAppBarSystem.rootTopAppBarStyle
            },
            title = stringResource(Res.string.title_settings),
            onBackClick = { navigator.goBack() },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .weight(weight = 1F)
                .verticalScroll(state = rememberScrollState())
        ) {
            GenreSelectionModeSettingsComponent(
                genresSelectionModeState = genresSelectionModeState,
                onGenresSelectionModeChanged = viewModel::onGenresSelectionModeChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(insets = insetsCutoutHorizontal)
                    .windowInsetsPadding(insets = insetsNavBarsHorizontal)
            )

            Spacer(modifier = Modifier.weight(weight = 1F))

            Text(
                text = stringResource(Res.string.platform_name, getPlatform().name),
                style = AppTheme.typographySystem.commonCheckboxLabel,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(all = 16.dp)
                    .then(if (isFullScreen) Modifier.navigationBarsPadding() else Modifier)
            )
        }
    }
}

@Composable
private fun GenreSelectionModeSettingsComponent(
    genresSelectionModeState: MutableState<GenresSelectionMode>,
    onGenresSelectionModeChanged: (GenresSelectionMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkboxModifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)

    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.title_genres_selection_settings),
            style = AppTheme.typographySystem.settingsHeader,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        )

        GenresSelectionMode.entries.forEach { selectionMode ->
            CheckboxComponent(
                isChecked = selectionMode == genresSelectionModeState.value,
                style = AppTheme.buttonSystem.checkboxSystem.commonCheckboxStyle,
                onCheckedChange = {
                    genresSelectionModeState.value = selectionMode
                    onGenresSelectionModeChanged(selectionMode)
                },
                label = selectionMode.defineLabel(),
                modifier = checkboxModifier
            )
        }
    }
}