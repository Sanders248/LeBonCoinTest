package com.example.leboncointest.api

import com.example.leboncointest.data.Album
import io.reactivex.Single
import retrofit2.http.GET

interface IAlbumApi {
    @GET ("photos")
    fun getAlbumList() : Single<List<Album>>
}