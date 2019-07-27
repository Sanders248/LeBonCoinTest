package com.example.leboncointest.api

import com.example.leboncointest.data.Album
import io.reactivex.Observable
import retrofit2.http.GET

interface IAlbumApi {
    @GET ("photos")
    fun getAlbumList() : Observable<List<Album>>
}