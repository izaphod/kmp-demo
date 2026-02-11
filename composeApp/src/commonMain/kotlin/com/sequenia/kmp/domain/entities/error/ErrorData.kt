package ru.sequenia.test.domain.entities.error

import kotlinx.serialization.Serializable

@Serializable
data class ErrorData(
    val message: String?,
)