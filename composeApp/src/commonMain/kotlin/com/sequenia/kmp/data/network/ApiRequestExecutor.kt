package com.sequenia.kmp.data.network

import kotlinx.serialization.json.Json
import com.sequenia.kmp.data.entities.response.ErrorResponse
import com.sequenia.kmp.data.entities.error.HTTPErrorData
import ru.sequenia.test.domain.entities.error.ErrorData
import com.sequenia.kmp.domain.results.ExecutionResult

class ApiRequestExecutor(
    private val networkRequestExecutor: NetworkRequestExecutor,
    private val json: Json
) {

    suspend fun <T> execute(
        request: suspend () -> T
    ): ExecutionResult<T> {
        val result = networkRequestExecutor.execute { request.invoke() }

        return when (result) {
            is NetworkResult.Success -> ExecutionResult.Success(result.data)
            is NetworkResult.Error -> convertNetworkErrorToError(result.error)
        }
    }

    private fun convertNetworkErrorToError(
        networkError: NetworkError
    ): ExecutionResult.Error {
        return when (networkError) {
            is NetworkError.HTTP -> {
                val errorResponse = parseErrorBody(networkError.data)
                convertToExecutionError(errorResponse)
            }
            is NetworkError.Failure -> ExecutionResult.Error.Failure(networkError.throwable)
        }
    }

    private fun parseErrorBody(httpErrorData: HTTPErrorData): Any {
        return try {
            json.decodeFromString<ErrorResponse>(httpErrorData.errorBody ?: "{}")
        } catch (throwable: Throwable) {
            throwable
        }
    }

    private fun convertToExecutionError(parsedError: Any): ExecutionResult.Error {
        return when (parsedError) {
            is ErrorResponse -> {
                val errorData = ErrorData(parsedError.message)
                ExecutionResult.Error.Expected(errorData)
            }
            is Throwable -> {
                val networkThrowable = NetworkThrowable.ServerError(parsedError)
                ExecutionResult.Error.Failure(networkThrowable)
            }
            else -> ExecutionResult.Error.Failure(ApplicationThrowable(Throwable("Unknown error")))
        }
    }
}