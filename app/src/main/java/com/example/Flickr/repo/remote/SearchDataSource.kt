package com.example.Flickr.repo.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.Flickr.data.NetworkState
import com.example.Flickr.data.Photo
import com.example.Flickr.data.Result
import com.example.Flickr.data.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchDataSource @Inject constructor(

    private val remoteDataSource: SearchRemoteDataSource,
    private val coroutineScope: CoroutineScope,
    private val query: String

) : PageKeyedDataSource<Int, Photo>() {


    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial (
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(query,1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }


    }



    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        networkState.postValue(NetworkState.LOADING)
        val page = params.key
        fetchData(query,page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {

        val page = params.key
        fetchData(query,page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(query: String,page: Int, pageSize: Int, callback: (List<Photo>) -> Unit) {

        coroutineScope.launch(getJobErrorHandler()) {

            val response = remoteDataSource.search(query,page)

            Log.e("SearchRepo"," $response")


            if (response.status == Result.Status.SUCCESS) {

                val results = response.data?.photos?.photos

                callback(results!!)
                networkState.postValue(NetworkState.LOADED)

            } else if (response.status == Result.Status.ERROR) {
                networkState.postValue(NetworkState.error(response.message ?: "unknown err"))
                postError(response.message!!)
            }


        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {

        networkState.postValue(NetworkState.error(message))
    }


}