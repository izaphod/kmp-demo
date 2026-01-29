package com.sequenia.kmp.presentation

import androidx.compose.runtime.Composable
import com.sequenia.kmp.di.appKoinConfiguration
import com.sequenia.kmp.presentation.compose.theme.AppTheme
import com.sequenia.kmp.presentation.navigation.main.MainNavigationRoot
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(configuration = appKoinConfiguration()) {
        AppTheme {
            MainNavigationRoot()
        }
    }
}