package co.android.wframadhan.themoviesuperapp.ui.detail

import co.android.wframadhan.domain.models.Trailer

sealed class DetailViewState {

    data class Presenting( val results: List<Trailer>) : DetailViewState()

    object Error : DetailViewState()

    object Loading : DetailViewState()
}