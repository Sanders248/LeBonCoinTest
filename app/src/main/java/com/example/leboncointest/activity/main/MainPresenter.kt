package com.example.leboncointest.activity.main

import android.util.Log
import com.example.leboncointest.activity.BasePresenter
import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import com.example.leboncointest.tools.SharedPreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val sharedPreferenceManager: SharedPreferenceManager?)
    : BasePresenter<MainContract.view>(), MainContract.Presenter {

    private val TAG = "MainPresenter"

    private var disposable: Disposable? = null

    /**
     * load the list of Album from the API if connected to network or from SharedPreference if not
     */
    override fun showListView() {
        if (getView()?.isNetworkAvailable() == true) {
            disposable = AlbumApiManager.albumApi.getAlbumList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { albumList ->
                        sharedPreferenceManager?.putAlbumList(albumList)
                        updateAlbumListView(albumList)
                    },
                    onError = { Log.e(TAG, "getAlbumList onError: $it") }
                )
        } else {
            val albumList = sharedPreferenceManager?.getAlbumList() ?: emptyList()
            updateAlbumListView(albumList)
        }
    }

    override fun dispose() {
        if (disposable?.isDisposed == false) disposable?.dispose()
    }

    private fun updateAlbumListView(albumList: List<Album>) {
        getView()?.reloadAlbumList(albumList)
        getView()?.showProgressBar(false)
    }
}