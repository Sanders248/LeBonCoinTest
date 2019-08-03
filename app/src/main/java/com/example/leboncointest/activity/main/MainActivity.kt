package com.example.leboncointest.activity.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.leboncointest.R
import com.example.leboncointest.data.Album
import com.example.leboncointest.tools.CommonUtils
import com.example.leboncointest.tools.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity display a list of Album
 * It show the image (from url) and the title of each Album
 */
class MainActivity : AppCompatActivity(), MainContract.view {
    private val TAG = "MainActivity"

    private lateinit var viewAdapter: ImageListAdapter
    private lateinit var presenter: MainPresenter

    // activity lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(SharedPreferenceManager(applicationContext)).apply { setView(this@MainActivity) }

        initListView()

        presenter.showListView()
    }

    override fun onPause() {
        super.onPause()
        presenter.dispose()
    }

    // init methods

    private fun initListView() {
        val viewManager = LinearLayoutManager(this)
        viewAdapter = ImageListAdapter(emptyList())

        listView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    // MainContract.view

    override fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun reloadAlbumList(albumList: List<Album>) {
        viewAdapter.albumList = albumList
        viewAdapter.notifyDataSetChanged()
    }

    override fun isNetworkAvailable(): Boolean = CommonUtils.isNetworkAvailable(this)
}