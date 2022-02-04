package co.android.wframadhan.domain.repositories

import co.android.wframadhan.domain.models.Movie
import co.android.wframadhan.domain.models.Trailer
import io.reactivex.Single

interface MovieRepo {

    fun getTopRatedMovies(sort: String): Single<List<Movie>>

    fun getTrailers(id: Int): Single<List<Trailer>>

}