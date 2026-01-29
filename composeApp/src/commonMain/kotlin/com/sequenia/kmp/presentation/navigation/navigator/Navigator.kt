package com.sequenia.kmp.presentation.navigation.navigator

import androidx.navigation3.runtime.NavKey

interface Navigator {

    fun navigate(key: NavKey)

    fun navigate(
        key: NavKey,
        popUpTo: NavKey,
        isInclusive: Boolean = false,
        findLast: Boolean = true
    )

    fun goBack()
}