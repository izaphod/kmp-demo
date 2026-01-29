package com.sequenia.kmp.presentation.navigation.navigator

import androidx.navigation3.runtime.NavKey
import com.sequenia.kmp.presentation.navigation.state.MultiStackNavigationState

/**
 * Handles navigation events (forward and back) by updating the navigation state.
 *
 * @param state - The navigation state that will be updated in response to navigation events.
 */
internal class MultiStackNavigator(val state: MultiStackNavigationState) : Navigator {

    /**
     * Navigate to a navigation key
     *
     * @param key - the navigation key to navigate to.
     */
    override fun navigate(key: NavKey) {
        when (key) {
            state.currentTopLevelKey -> clearSubStack()
            in state.topLevelKeys -> goToTopLevel(key)
            else -> goToKey(key)
        }
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
        when (state.currentKey) {
            state.startKey -> error("You cannot go back from the start route")
            state.currentTopLevelKey -> {
                // We're at the base of the current sub stack, go back to the previous top level
                // stack.
                state.topLevelStack.removeLastOrNull()
            }
            else -> state.currentSubStack.removeLastOrNull()
        }
    }

    /**
     * Go to a non top level key.
     */
    private fun goToKey(key: NavKey) {
        state.currentSubStack.add(key)
    }

    /**
     * Go to a top level stack.
     */
    private fun goToTopLevel(key: NavKey) {
        state.topLevelStack.apply {
            if (key == state.startKey) {
                // This is the start key. Clear the stack so it's added as the only key.
                clear()
            }
            add(key)
        }
    }

    /**
     * Clearing all but the root key in the current sub stack.
     */
    private fun clearSubStack() {
        state.currentSubStack.run {
            if (size > 1) subList(1, size).clear()
        }
    }

    /**
     * Pop up the navigation stack to a specific key.
     * Only works for keys in the current sub stack, not for top level keys.
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
        val currentSubStack = state.currentSubStack
        val subStackIndex = if (findLast) {
            currentSubStack.lastIndexOf(popUpTo)
        } else {
            currentSubStack.indexOf(popUpTo)
        }

        if (subStackIndex != -1) {
            // Found in current sub stack
            val startIndex = if (isInclusive) subStackIndex else subStackIndex + 1
            if (startIndex < currentSubStack.size) {
                currentSubStack.subList(startIndex, currentSubStack.size).clear()
            }
        }
    }
}