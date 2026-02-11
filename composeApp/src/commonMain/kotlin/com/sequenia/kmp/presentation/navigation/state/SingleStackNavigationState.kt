/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sequenia.kmp.presentation.navigation.state

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.serialization.SavedStateConfiguration

/**
 * Create a simple navigation state (single stack, no sub-stacks) that persists config changes and process death.
 * Use this for top-level navigation where you don't need hierarchical navigation.
 */
@Composable
fun rememberSimpleNavigationState(
    startKey: NavKey,
    configuration: SavedStateConfiguration
): SingleStackNavigationState {
    val backStack = rememberNavBackStack(configuration, startKey)

    return remember(startKey) {
        SingleStackNavigationState(
            startKey = startKey,
            backStack = backStack,
        )
    }
}

/**
 * Simple navigation state holder with a single back stack.
 * No sub-stacks - just a linear navigation stack.
 *
 * @param startKey - the starting navigation key. The user will exit the app through this key.
 * @param backStack - the back stack holding navigation keys.
 */
class SingleStackNavigationState(
    val startKey: NavKey,
    val backStack: NavBackStack<NavKey>,
) {
    @get:VisibleForTesting
    val currentKey: NavKey by derivedStateOf { backStack.last() }
}

/**
 * Convert SimpleNavigationState into NavEntries.
 */
@Composable
fun SingleStackNavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>,
): SnapshotStateList<NavEntry<NavKey>> {
    val decorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
        rememberViewModelStoreNavEntryDecorator(),
    )
    
    val decoratedEntries = rememberDecoratedNavEntries(
        backStack = backStack,
        entryDecorators = decorators,
        entryProvider = entryProvider,
    )

    return decoratedEntries.toMutableStateList()
}
