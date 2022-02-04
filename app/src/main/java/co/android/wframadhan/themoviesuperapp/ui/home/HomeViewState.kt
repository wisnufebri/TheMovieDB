package co.android.wframadhan.themoviesuperapp.ui.home

import co.android.wframadhan.domain.models.Movie

sealed class HomeViewState {

    data class Presenting( val results: List<Movie>) : HomeViewState()

    object Error : HomeViewState()

    object Loading : HomeViewState()
}