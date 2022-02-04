package co.android.wframadhan.data.api.response

import co.android.wframadhan.data.models.TrailerDTO
import com.squareup.moshi.Json

data class TrailersResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<TrailerDTO>
)