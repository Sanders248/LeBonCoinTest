package com.example.leboncointest.api

import com.example.leboncointest.data.Album
import retrofit2.Retrofit
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AlbumApiManager {
    private val albumApi: IAlbumApi
    private val baseUrl = "http://jsonplaceholder.typicode.com/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        albumApi = retrofit.create(IAlbumApi::class.java)
    }

    fun getAlbumList(observer: Observer<List<Album>>) {
        albumApi.getAlbumList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}