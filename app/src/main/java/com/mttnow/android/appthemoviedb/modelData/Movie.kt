package com.mttnow.android.appthemoviedb.modelData

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val movieId: Int = 0,
    @SerializedName("poster_path") val poster_path:String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("title") val title: String= "",
    @SerializedName("original_title") val orTitle: String = "",
    @SerializedName("vote_average") val vote: Float
)
