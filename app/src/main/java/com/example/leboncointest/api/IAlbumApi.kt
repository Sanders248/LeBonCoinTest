package com.example.leboncointest.api

import com.example.leboncointest.data.AlbumResponse
import io.reactivex.Single
import retrofit2.http.GET

interface IAlbumApi {
    @GET ("photos")
    fun getAlbumList() : Single<List<AlbumResponse>>
}