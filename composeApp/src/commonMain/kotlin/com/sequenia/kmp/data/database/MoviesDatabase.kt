package com.sequenia.kmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [MovieEntity::class], version = 1)
@ConstructedBy(MoviesDatabaseConstructor::class)
@TypeConverters(MovieTypeConverters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}

@Suppress("KotlinNoActualForExpect")
expect object MoviesDatabaseConstructor : RoomDatabaseConstructor<MoviesDatabase> {
    override fun initialize(): MoviesDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MoviesDatabase>
): MoviesDatabase {
    return builder
        .setDriver(driver = BundledSQLiteDriver())
        .setQueryCoroutineContext(context = Dispatchers.IO)
        .build()
}