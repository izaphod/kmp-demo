package com.sequenia.kmp.presentation.navigation

class CommonNavigator(
    mainNavState: NavigationState,
    bottomNavState: NavigationState
) {

    val mainNavigator = Navigator(mainNavState)
    val bottomNavNavigator = Navigator(bottomNavState)
}