package com.example.Flickr.repo

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.example.Flickr.data.Data
import com.example.Flickr.data.Photo
import com.example.Flickr.repo.remote.SearchDataSourceFactory
import com.example.Flickr.repo.remote.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepo @Inject constructor(private val searchRemoteDataSource: SearchRemoteDataSource,
                                     private val coroutineScope: CoroutineScope
) {

    fun searchPhoto(query: String): Data<Photo> {
        Log.e("SearchRepo"," $query ")
        val dataSourceFactory = SearchDataSourceFactory(searchRemoteDataSource,
            coroutineScope,query
        )



        val networkState = Transformations.switchMap(dataSourceFactory.liveData) {
            it.networkState
        }

        return Data(
            LivePagedListBuilder(dataSourceFactory,
                SearchDataSourceFactory.pagedListConfig()).build(),networkState)

    }


}