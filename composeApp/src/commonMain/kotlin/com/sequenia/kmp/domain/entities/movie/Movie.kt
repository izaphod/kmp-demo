package com.sequenia.kmp.domain.entities.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long?,
    val name: String?,
    @SerialName("localized_name")
    val localizedName: String?,
    val year: Int?,
    val rating: Float?,
    @SerialName("image_url")
    val posterUrl: String?,
    val description: String?,
    val genres: List<String>?
)