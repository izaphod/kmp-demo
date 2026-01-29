package com.sequenia.kmp.presentation.navigation.navigator

import androidx.navigation3.runtime.NavKey
import com.sequenia.kmp.presentation.navigation.state.SingleStackNavigationState

/**
 * Simple navigator for SimpleNavigationState.
 * Handles navigation events for a single-stack navigation state.
 *
 * @param state - The simple navigation state that will be updated in response to navigation events.
 */
internal class SingleStackNavigator(val state: SingleStackNavigationState) : Navigator {

    /**
     * Navigate to a navigation key
     *
     * @param key - the navigation key to navigate to.
     */
    override fun navigate(key: NavKey) {
        if (key == state.startKey) {
            // Clear stack if navigating to start key
            state.backStack.clear()
        }
        state.backStack.add(key)
    }

    /**
     * Navigate to a navigation key with stack clearing up to a specific key.
     *
     * @param key - the navigation key to navigate to.
     * @param popUpTo - the navigation key to pop up to. All keys after this key
     * (or including it if isInclusive is true) will be removed from the stack.
     * @param isInclusive - if true, the popUpTo key itself will also be removed
     * from the stack. Default is false.
     * @param findLast - if true, searches for the last occurrence of popUpTo in
     * the stack (closest to the end). If false, searches for the first
     * occurrence. Default is true.
     */
    override fun navigate(
        key: NavKey,
        popUpTo: NavKey,
        isInclusive: Boolean,
        findLast: Boolean
    ) {
        popUpTo(popUpTo, isInclusive, findLast)
        navigate(key)
    }

    /**
     * Go back to the previous navigation key.
     */
    override fun goBack() {
        if (state.currentKey == state.startKey) {
            error("You cannot go back from the start route")
        }
        state.backStack.removeLastOrNull()
    }

    /**
     * Pop up the navigation stack to a specific key.
     *
     * @param popUpTo - the navigation key to pop up to.
     * @param isInclusive - if true, the popUpTo key itself will also be removed
     * from the stack.
     * @param findLast - if true, searches for the last occurrence of popUpTo in
     * the stack (closest to the end). If false, searches for the first
     * occurrence.
     */
    private fun popUpTo(
        popUpTo: NavKey,
        isInclusive: Boolean,
        findLast: Boolean
    ) {
        val stackIndex = if (findLast) {
            state.backStack.lastIndexOf(popUpTo)
        } else {
            state.backStack.indexOf(popUpTo)
        }

        if (stackIndex != -1) {
            val startIndex = if (isInclusive) stackIndex else stackIndex + 1
            if (startIndex < state.backStack.size) {
                state.backStack.subList(startIndex, state.backStack.size).clear()
            }
        }
    }
}