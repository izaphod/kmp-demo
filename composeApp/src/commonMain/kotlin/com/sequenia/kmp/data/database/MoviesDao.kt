package com.sequenia.kmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getMoviesFlow(): Flow<List<MovieEntity>>

    @Transaction
    suspend fun replaceAllMovies(movies: List<MovieEntity>) {
        clearAll()
        insertMovies(movies)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}