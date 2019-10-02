package com.example.leboncointest.activity.main

import android.app.Application
import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import com.example.leboncointest.tools.SharedPreferenceManager
import io.reactivex.Single

class UseCaseMain {
    fun getAlbumListFromApi(): Single<List<Album>> = AlbumApiManager.albumApi.getAlbumList()
        .map { albumList ->
            albumList.filter { it.title != null && it.url != null  }.map { Album(it.title!!, it.url!!) }
        }

    fun getAlbumListFromPreferences(application: Application) : List<Album> = SharedPreferenceManager(application).getAlbumList()

    fun putAlbumListIntoPreferences(application: Application, albumList:List<Album>) = SharedPreferenceManager(application).putAlbumList(albumList)
}