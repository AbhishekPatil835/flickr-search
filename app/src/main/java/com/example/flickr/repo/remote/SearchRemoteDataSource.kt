package com.example.flickr.repo.remote

import com.example.flickr.OpenForTesting
import com.example.flickr.data.BaseDataSource
import com.example.flickr.data.FlickrApi
import com.example.flickr.data.Result
import com.example.flickr.data.SearchResult
import javax.inject.Inject

@OpenForTesting
class SearchRemoteDataSource @Inject constructor(val service: FlickrApi) : BaseDataSource() {

    val map = HashMap<String,String>()

    init {
        map["method"] = "flickr.photos.search"
        map["api_key"] = "3e7cc266ae2b0e0d78e279ce8e361736"
        map["format"] = "json"

    }

     fun search(perPage:Int,query: String,page:Int) : Result<SearchResult> {

        map["text"] = query
        return getResult { service.searchPhotos(perPage,page,map) }
    }

}