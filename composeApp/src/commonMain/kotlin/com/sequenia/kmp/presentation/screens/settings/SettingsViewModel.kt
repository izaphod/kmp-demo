package ru.sequenia.test.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sequenia.kmp.domain.entities.settings.GenresSelectionMode
import com.sequenia.kmp.domain.models.settings.SettingsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.sequenia.kmp.presentation.screens.settings.SettingsScreenState as ScreenState

class SettingsViewModel(
    private val settingsModel: SettingsModel
) : ViewModel() {

    val screenStateFlow get() = mutableScreenState.asStateFlow()

    private val mutableScreenState = MutableStateFlow<ScreenState>(ScreenState.InitialState)

    private var genresSelectionMode = GenresSelectionMode.MULTIPLE

    init {
        viewModelScope.launch { getGenresSelectionMode() }
    }

    fun onGenresSelectionModeChanged(mode: GenresSelectionMode) {
        genresSelectionMode = mode
        viewModelScope.launch { saveGenresSelectionMode() }
    }

    private suspend fun getGenresSelectionMode() {
        genresSelectionMode = settingsModel.getGenresSelectionMode()
        changeScreenStateToSuccess()
    }

    private suspend fun saveGenresSelectionMode() {
        settingsModel.saveGenresSelectionMode(genresSelectionMode)
    }

    private fun changeScreenStateToSuccess() {
        mutableScreenState.value = ScreenState.SuccessState(genresSelectionMode)
    }
}