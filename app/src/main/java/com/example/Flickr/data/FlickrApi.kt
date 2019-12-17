package com.example.Flickr.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FlickrApi {

    companion object {
        val BASE_URL = "https://api.flickr.com/services/rest/"
    }

    @GET(".")
    suspend fun searchPhotos(@Query("per_page") perpage: Int,
                             @Query("page") page: Int,
                             @QueryMap options: Map<String,String>,
                             @Query("nojsoncallback") noJson: Int = 1): Response<SearchResult>
}