package com.example.leboncointest.activity.main

import com.example.leboncointest.data.Album

interface MainContract {
    interface Presenter {
        fun showListView()
        fun dispose()
    }

    interface view {
        fun reloadAlbumList(albumList: List<Album>)
        fun showProgressBar(show: Boolean)
        fun isNetworkAvailable() : Boolean
    }
}