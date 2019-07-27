package com.example.leboncointest

import android.util.Log
import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import com.example.leboncointest.tools.CommonUtils
import com.example.leboncointest.tools.SharedPreferenceManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainPresenter(val activity: MainActivity) {
    val TAG = "MainPresenter"

    /**
     * load the list of Album from the API if connected to network or from SharedPreference if not
     */
    fun showListView() {
        if (CommonUtils.isNetworkAvailable(activity)) {
            AlbumApiManager().getAlbumList(object : Observer<List<Album>> {
                override fun onComplete() {
                    activity.showProgressBar(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(albumList: List<Album>) {
                    SharedPreferenceManager(activity).putAlbumList(albumList)
                    activity.reloadAlbumList(albumList)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "getAlbumList onError: $e")
                }
            })
        } else {
            val albumList = SharedPreferenceManager(activity).getAlbumList()
            activity.showProgressBar(false)
            activity.reloadAlbumList(albumList)
        }
    }
}