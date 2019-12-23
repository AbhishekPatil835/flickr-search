package com.example.flickr.repo.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.flickr.data.Photo
import javax.inject.Inject

class SearchDataSourceFactory @Inject constructor(private val searchRemoteDataSource: SearchRemoteDataSource,
                                                  private val query: String): DataSource.Factory<Int,Photo>() {
    val liveData = MutableLiveData<SearchDataSource>()


    override fun create(): DataSource<Int, Photo> {

        val source = SearchDataSource(searchRemoteDataSource,query)

        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 30

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(2 * PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}