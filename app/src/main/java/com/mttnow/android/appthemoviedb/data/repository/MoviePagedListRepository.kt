package com.mttnow.android.appthemoviedb.data.repository

import androidx.paging.PagedList
import androidx.lifecycle.LiveData
import com.mttnow.android.appthemoviedb.data.apiNetwork.TMDBInterface
import com.mttnow.android.appthemoviedb.modelData.Movie


class MoviePagedListRepository(private val apiInterface: TMDBInterface) {
    lateinit var moviePageList: LiveData<PagedList<Movie>>
}