package com.example.Flickr.repo.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.Flickr.data.Photo
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SearchDataSourceFactory @Inject constructor(private val searchRemoteDataSource: SearchRemoteDataSource,
                                                  private val coroutineScope: CoroutineScope,
                                                  private val query: String): DataSource.Factory<Int,Photo>() {
    val liveData = MutableLiveData<SearchDataSource>()


    override fun create(): DataSource<Int, Photo> {

        val source = SearchDataSource(searchRemoteDataSource,coroutineScope,query)

        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}