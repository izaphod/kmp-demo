package com.sequenia.kmp.presentation.compose.extension

import androidx.compose.runtime.Composable
import com.sequenia.kmp.presentation.extensions.defineStringResource
import org.jetbrains.compose.resources.stringResource
import com.sequenia.kmp.domain.results.ExecutionResult

@Composable
fun ExecutionResult.Error.defineMessage(): String {
    return when (this) {
        is ExecutionResult.Error.Expected -> data.message.orEmpty()
        is ExecutionResult.Error.Failure -> stringResource(throwable.defineStringResource())
    }
}