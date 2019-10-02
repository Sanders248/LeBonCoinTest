package com.example.leboncointest.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object AlbumApiManager {
    private const val baseUrl = "http://jsonplaceholder.typicode.com/"

    val albumApi: IAlbumApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        albumApi = retrofit.create(IAlbumApi::class.java)
    }
}