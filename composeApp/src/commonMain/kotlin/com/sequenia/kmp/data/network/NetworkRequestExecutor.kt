package com.sequenia.kmp.data.network

import com.sequenia.kmp.data.entities.error.HTTPErrorData
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException

class NetworkRequestExecutor {

    suspend fun <T> execute(
        request: suspend () -> T
    ): NetworkResult<T> {
        return try {
            val data = request.invoke()
            NetworkResult.Success(data)
        } catch (throwable: Throwable) {
            val networkError = convertThrowableToNetworkError(throwable)
            NetworkResult.Error(networkError)
        }
    }

    private suspend fun convertThrowableToNetworkError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is ResponseException -> {
                val errorBody = throwable.response.bodyAsText()
                val errorData = HTTPErrorData(
                    code = throwable.response.status.value,
                    errorBody = errorBody
                )
                NetworkError.HTTP(errorData)
            }

            else -> {
                val networkThrowable = convertThrowableToNetworkThrowable(throwable)
                NetworkError.Failure(networkThrowable)
            }
        }
    }

    private fun convertThrowableToNetworkThrowable(throwable: Throwable): NetworkThrowable {
        return when (throwable) {
            is CancellationException -> throw throwable
            is IOException -> NetworkThrowable.IO(cause = throwable)
            is HttpRequestTimeoutException -> NetworkThrowable.Timeout(cause = throwable)
            is SerializationException -> NetworkThrowable.Parsing(cause = throwable)
            else -> NetworkThrowable.Unknown(cause = throwable)
        }
    }
}