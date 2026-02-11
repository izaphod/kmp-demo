package com.sequenia.kmp.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

@Suppress("unused")
object MovieTypeConverters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(it) }
    }
}