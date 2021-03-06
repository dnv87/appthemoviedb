package com.mttnow.android.appthemoviedb.data.repository


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.appthemoviedb.data.apiNetwork.TMDBInterface
import com.mttnow.android.appthemoviedb.modelData.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : TMDBInterface,
private val compositeDisposable: CompositeDisposable)
: DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}