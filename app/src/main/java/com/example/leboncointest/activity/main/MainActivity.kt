package com.example.leboncointest.activity.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewAdapter: ImageListAdapter

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initListView()
        showProgressBar(true)

        viewModel.loadAlbumList()

        viewModel.albumList.observe(this, Observer {
            it?.let {
                reloadAlbumList(it)
                showProgressBar(false)
            }
        })
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

    private fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun reloadAlbumList(albumList: List<Album>) {
        viewAdapter.albumList = albumList
        viewAdapter.notifyDataSetChanged()
    }
}