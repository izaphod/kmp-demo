package com.sequenia.kmp.data.network

sealed class NetworkResult<out T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error(val error: NetworkError) : NetworkResult<Nothing>()
}