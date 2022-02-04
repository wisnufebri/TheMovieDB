package co.android.wframadhan.themoviesuperapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.android.wframadhan.data.repositories.MovieRepoImpl
import co.android.wframadhan.domain.models.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieRepoImpl: MovieRepoImpl) : ViewModel() {

    fun onViewInit(movie: Movie) {
        _selectedMovie.value = movie
        _movieId.value = movie.id
        movieId.value?.let { getTrailers(it) }
    }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title
    fun updateActionBarTitle(title: String) = _title.postValue(title)


    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState>
        get() = _viewState

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int>
        get() = _movieId


    private fun getTrailers(id: Int) {
        _viewState.value = DetailViewState.Loading
        add(
            movieRepoImpl.getTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = DetailViewState.Presenting(it)
                }, {
                    _viewState.value = DetailViewState.Error
                }

                ))
    }

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}