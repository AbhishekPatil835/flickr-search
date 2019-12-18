package com.example.Flickr.repo.remote

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.example.Flickr.data.BaseDataSource
import com.example.Flickr.data.FlickrApi
import com.example.Flickr.data.Result
import com.example.Flickr.data.SearchResult
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(val service: FlickrApi) : BaseDataSource() {

    val map = HashMap<String,String>()

    init {
        map["method"] = "flickr.photos.search"
        map["api_key"] = "062a6c0c49e4de1d78497d13a7dbb360"
        map["format"] = "json"

    }

    suspend fun search(query: String,page:Int) : Result<SearchResult> {

        map["text"] = query
        return getResult { service.searchPhotos(20,page,map) }
    }

}