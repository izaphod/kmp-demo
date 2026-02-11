package com.sequenia.kmp.data.data_sources.movies

import com.sequenia.kmp.data.database.MovieEntity
import com.sequenia.kmp.data.database.MoviesDao
import com.sequenia.kmp.domain.entities.movie.Movie
import kotlinx.coroutines.flow.first

class MoviesDataSourceLocalImpl(
    private val moviesDao: MoviesDao
) : MoviesDataSourceLocal {

    override suspend fun loadMovies(): List<Movie> {
        val entities = moviesDao.getMoviesFlow().first()

        return convertMovieEntitiesToMovies(entities)
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        val entities = convertMoviesToMovieEntities(movies)
        moviesDao.replaceAllMovies(entities)
    }

    private fun convertMoviesToMovieEntities(movies: List<Movie>): List<MovieEntity> {
        return movies.map { movie -> convertMovieToMovieEntity(movie) }
    }

    private fun convertMovieEntitiesToMovies(entities: List<MovieEntity>): List<Movie> {
        return entities.map { entity -> convertMovieEntityToMovie(entity) }
    }

    private fun convertMovieToMovieEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            name = movie.name,
            localizedName = movie.localizedName,
            year = movie.year,
            rating = movie.rating,
            posterUrl = movie.posterUrl,
            description = movie.description,
            genres = movie.genres,
        )
    }

    private fun convertMovieEntityToMovie(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            name = entity.name,
            localizedName = entity.localizedName,
            year = entity.year,
            rating = entity.rating,
            posterUrl = entity.posterUrl,
            description = entity.description,
            genres = entity.genres,
        )
    }
}