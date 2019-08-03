package com.example.leboncointest.activity.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.leboncointest.R
import com.example.leboncointest.data.Album
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImageListAdapter(var albumList: List<Album>) : RecyclerView.Adapter<ImageListAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AlbumViewHolder {
        val inflatedView = LayoutInflater.from(p0.context).inflate(R.layout.image_list_item, p0, false)
        return AlbumViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.bindAlbum(album)
    }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var album: Album? = null

        /**
         * Bind an album to the itemView and keep image in cache
         */
        fun bindAlbum(album: Album) {
            this.album = album
            Glide.with(itemView.context)
                .load(album.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.itemImage)

            itemView.itemTitle.text = album.title
        }
    }
}