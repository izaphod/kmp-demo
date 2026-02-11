package com.sequenia.kmp.presentation.navigation.navigator

import com.sequenia.kmp.presentation.navigation.state.MultiStackNavigationState
import com.sequenia.kmp.presentation.navigation.state.SingleStackNavigationState

class CommonNavigator(
    mainNavState: SingleStackNavigationState,
    bottomNavState: MultiStackNavigationState
) {

    internal val mainNavigator = SingleStackNavigator(mainNavState)
    internal val bottomNavNavigator = MultiStackNavigator(bottomNavState)
}