package com.example.Flickr.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.Flickr.R
import com.example.Flickr.data.Photo
import com.example.Flickr.extensions.load
import kotlinx.android.synthetic.main.rv_photo_item.view.*

class SearchAdapter : PagedListAdapter<Photo, SearchAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_photo_item,parent,false))
    }



    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind( item: Photo?) {
            with(itemView) {
                item?.let {
                    ivPhoto.load(it.getUrl())
                }
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}