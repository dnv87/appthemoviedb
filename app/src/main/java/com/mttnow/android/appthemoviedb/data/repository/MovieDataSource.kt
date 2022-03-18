package com.mttnow.android.appthemoviedb.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mttnow.android.appthemoviedb.data.Const
import com.mttnow.android.appthemoviedb.data.apiNetwork.NetworkState
import com.mttnow.android.appthemoviedb.data.apiNetwork.TMDBInterface
import com.mttnow.android.appthemoviedb.modelData.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource (private val interfaceApi: TMDBInterface,
                                private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Movie>()
{

    private var pageMovie = Const.FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial (params: LoadInitialParams<Int>,
                                      callback: LoadInitialCallback<Int, Movie>){

        networkState.postValue(NetworkState.LOADING) // данные приходят из фонового потока

        compositeDisposable.add(
            interfaceApi.getPopularMovie(page = pageMovie)
                .subscribeOn(Schedulers.io()) //с помощью этого оператора можно указать Scheduler, в котором будет выполняться процесс Observable
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, pageMovie+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            interfaceApi.getPopularMovie(page = params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.total_pages >= params.key) {
                            callback.onResult(it.movieList, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

}


