package co.android.wframadhan.themoviesuperapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.android.wframadhan.data.repositories.MovieRepoImpl
import co.android.wframadhan.domain.models.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val movieRepoImpl: MovieRepoImpl): ViewModel()  {

    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie

    fun onViewLoaded() {
        getTopRatedMovies("popularity.desc")
    }


    private fun getTopRatedMovies(sortBy : String) {

        _viewState.value = HomeViewState.Loading
        add(
            movieRepoImpl.getTopRatedMovies(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = HomeViewState.Presenting(it)
                }, {
                    RxJavaPlugins.onError(it)
                    Log.d("TAG", "ERROR HOME VM")
                    _viewState.value = HomeViewState.Error
                }
                )
        )
    }

    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}