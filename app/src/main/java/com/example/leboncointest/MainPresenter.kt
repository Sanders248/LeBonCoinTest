package com.example.leboncointest

import android.util.Log
import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import com.example.leboncointest.tools.CommonUtils
import com.example.leboncointest.tools.SharedPreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainPresenter(val activity: MainActivity) {
    val TAG = "MainPresenter"

    var disposable: Disposable? = null

    /**
     * load the list of Album from the API if connected to network or from SharedPreference if not
     */
    fun showListView() {
        if (CommonUtils.isNetworkAvailable(activity)) {
            disposable = AlbumApiManager.albumApi.getAlbumList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { albumList ->
                        SharedPreferenceManager(activity).putAlbumList(albumList)
                        updateAlbumListView(albumList)
                    },
                    onError = {
                        Log.e(TAG, "getAlbumList onError: $it")
                    })
        } else {
            val albumList = SharedPreferenceManager(activity).getAlbumList()
            updateAlbumListView(albumList)
        }
    }

    private fun updateAlbumListView(albumList: List<Album>) {
        activity.reloadAlbumList(albumList)
        activity.showProgressBar(false)
    }
}