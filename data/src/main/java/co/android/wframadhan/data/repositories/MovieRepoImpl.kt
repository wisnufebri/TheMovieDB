package co.android.wframadhan.data.repositories

import co.android.wframadhan.data.api.MovieApi
import co.android.wframadhan.data.mappers.MovieDTOMapper
import co.android.wframadhan.data.mappers.TrailerDTOMapper
import co.android.wframadhan.data.repositories.MovieRepoImpl.Constants.API_KEY
import co.android.wframadhan.domain.models.Movie
import co.android.wframadhan.domain.models.Trailer
import co.android.wframadhan.domain.repositories.MovieRepo
import io.reactivex.Single
import javax.inject.Inject

class MovieRepoImpl@Inject constructor(
    private val movieApi: MovieApi,
    private val movieMapper: MovieDTOMapper,
    private val trailerMapper: TrailerDTOMapper,
) : MovieRepo {

    object Constants {
        const val API_KEY =  "ff4efe0abb28b1f61ba564bd5c619bd0"
    }

    override fun getTopRatedMovies(sortBy: String): Single<List<Movie>> {

        return movieApi.getTopRatedMovies(API_KEY, "en-us", sortBy,
            "false", "false", 1)
            .map {
                movieMapper.mapToDomainList(it.results)
            }
    }

    override fun getTrailers(id: Int): Single<List<Trailer>> {

        return movieApi.getTrailers(id, API_KEY, "en-us")
            .map {
                trailerMapper.mapToDomainList(it.results)
            }
    }
}