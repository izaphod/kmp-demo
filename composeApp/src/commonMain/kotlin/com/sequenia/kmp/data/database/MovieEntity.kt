package com.sequenia.kmp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Long?,
    val name: String?,
    val localizedName: String?,
    val year: Int?,
    val rating: Float?,
    val posterUrl: String?,
    val description: String?,
    val genres: List<String>?
)