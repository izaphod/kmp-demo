package com.sequenia.kmp.data.network

import com.sequenia.kmp.data.entities.error.HTTPErrorData

sealed class NetworkError {
    class HTTP(val data: HTTPErrorData) : NetworkError()
    class Failure(val throwable: NetworkThrowable) : NetworkError()
}