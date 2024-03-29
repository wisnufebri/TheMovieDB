package co.android.wframadhan.themoviesuperapp.ui.home

import co.android.wframadhan.domain.models.Movie

fun removeBrokenMovies(movies: List<Movie>): MutableList<Movie> {

    val cleanedMovies: MutableList<Movie> = mutableListOf()

    movies.forEach {
        if (it.id != null &&
            it.vote_average != null &&
            it.title != null &&
            it.release_date != null &&
            it.backdrop_path != null &&
            it.overview != null &&
            it.poster_path != null
        ) {
            cleanedMovies.add(it)
        }
    }

    return cleanedMovies.dropLast(20 - cleanedMovies.size) as MutableList<Movie>
}