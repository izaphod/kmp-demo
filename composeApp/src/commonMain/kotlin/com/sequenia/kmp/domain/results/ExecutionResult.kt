package ru.sequenia.test.domain.entities.results

import com.sequenia.kmp.data.network.ApplicationThrowable
import ru.sequenia.test.domain.entities.error.ErrorData

sealed class ExecutionResult<out Data> {

    class Success<Data>(val data: Data) : ExecutionResult<Data>()

    sealed class Error : ExecutionResult<Nothing>() {
        class Expected(val data: ErrorData) : Error()
        class Failure(val throwable: ApplicationThrowable) : Error()
    }
}