package com.example.Flickr.repo.remote

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.example.Flickr.OpenForTesting
import com.example.Flickr.data.BaseDataSource
import com.example.Flickr.data.FlickrApi
import com.example.Flickr.data.Result
import com.example.Flickr.data.SearchResult
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