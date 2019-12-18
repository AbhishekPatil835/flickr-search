package com.example.Flickr.search

import androidx.lifecycle.ViewModel
import com.example.Flickr.data.Data
import com.example.Flickr.data.Photo
import com.example.Flickr.repo.SearchRepo
import javax.inject.Inject

class SearchVM @Inject constructor(val searchRepo: SearchRepo) : ViewModel() {

    var data : Data<Photo>? = null



    var oldQuery: String  = ""

    fun search(query:String) : Data<Photo>? {
        if(data == null || oldQuery != query)
            data = searchRepo.searchPhoto(query)

        oldQuery = query
        return data
    }
}