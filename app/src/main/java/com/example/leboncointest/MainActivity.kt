package com.example.leboncointest

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import android.net.ConnectivityManager
import com.example.leboncointest.tools.SharedPreferenceManager


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListView()
        loadAlbumList()
    }

    private fun initListView() {
        val viewManager = LinearLayoutManager(this)
        viewAdapter = ImageListAdapter(emptyList())

        listView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun loadAlbumList() {
        val apiManager = AlbumApiManager()

        if (isNetworkAvailable())
            apiManager.getAlbumList(object : Observer<List<Album>> {
                override fun onComplete() {
                    progressBar.visibility = View.GONE
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(albumList: List<Album>) {
                    SharedPreferenceManager(this@MainActivity).putAlbumList(albumList)
                    viewAdapter.albumList = albumList
                    viewAdapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "getAlbumList onError: $e")
                }
            })
        else {
            val albumList = SharedPreferenceManager(this@MainActivity).getAlbumList()
            progressBar.visibility = View.GONE
            viewAdapter.albumList = albumList
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected == true
    }
}
