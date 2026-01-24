package com.sequenia.kmp.data.entities.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String?,
)