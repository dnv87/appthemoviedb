package com.mttnow.android.appthemoviedb.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.appthemoviedb.data.Const
import com.mttnow.android.appthemoviedb.data.apiNetwork.NetworkState
import com.mttnow.android.appthemoviedb.data.apiNetwork.TMDBInterface
import com.mttnow.android.appthemoviedb.data.repository.MovieDataSource
import com.mttnow.android.appthemoviedb.data.repository.MovieDataSourceFactory
import com.mttnow.android.appthemoviedb.modelData.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : TMDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}