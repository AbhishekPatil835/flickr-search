package com.example.flickr.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FlickrApi {

    companion object {
        val BASE_URL = "https://api.flickr.com/services/rest/"
    }

    @GET(".")
     fun searchPhotos(@Query("per_page") perpage: Int,
                             @Query("page") page: Int,
                             @QueryMap options: Map<String,String>,
                             @Query("nojsoncallback") noJson: Int = 1): Call<SearchResult>
}