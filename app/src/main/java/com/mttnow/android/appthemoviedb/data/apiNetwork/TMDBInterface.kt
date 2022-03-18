package com.mttnow.android.appthemoviedb.data.apiNetwork

import com.mttnow.android.appthemoviedb.data.Const
import com.mttnow.android.appthemoviedb.modelData.MovieDetails
import com.mttnow.android.appthemoviedb.modelData.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBInterface {
    @GET("movie/popular")
    fun getPopularMovie(
//        @Query("api_key") api_key: String = Const.API_KEY,
        @Query("page") page: Int,
        @Query("language") lang: String = "ru"
    ): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopMovie(
//        @Query("api_key") api_key: String = Const.API_KEY,
        @Query("page") page: Int,
        @Query("language") lang: String = "ru"
    ): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailsMovie(
//        @Query("api_key") api_key: String = Const.API_KEY,
        @Query("movie_id") id: Int,
        @Query("language") lang: String = "ru"
    ): Single<MovieDetails>
}