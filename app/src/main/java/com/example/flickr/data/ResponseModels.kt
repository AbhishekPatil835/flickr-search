package com.example.flickr.data

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("photos")
    var photos: SearchResponse
)

data class SearchResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("pages")
    var pages: Int,
    @SerializedName("perpage")
    var perpage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("photo")
    var photos: List<Photo>
)

data class Photo(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("owner")
    var owner: String? = null,
    @SerializedName("secret")
    var secret: String? = null,
    @SerializedName("server")
    var server: String? = null,
    @SerializedName("farm")
    var farm: Int? = 0,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("ispublic")
    var ispublic: Int,
    @SerializedName("isfriend")
    var isfriend: Int,
    @SerializedName("isfamily")
    var isfamily: Int
) {

    fun getUrl(): String {
        return "https://farm$farm.staticflickr.com/$server/$id"+"_"+"$secret"+"_"+"m.jpg"
    }

}