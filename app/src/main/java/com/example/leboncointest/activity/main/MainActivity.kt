package com.example.leboncointest.activity.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.leboncointest.R
import com.example.leboncointest.data.Album
import kotlinx.android.synthetic.main.activity_main.*
import com.example.leboncointest.activity.main.MainViewModel.AlbumState.*

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

        viewModel = ViewModelProviders.of(this, MainViewModel.BaseViewModelFactory(application, savedInstanceState)).get(MainViewModel::class.java)

        initListView()

        viewModel.albumList.observe(this, Observer {
            it?.getContent()?.let {
                when(it) {
                    is Loading -> showProgressBar(true)
                    is Failure -> {
                        Snackbar.make(listView, getString(R.string.error_loading), Snackbar.LENGTH_SHORT).show()
                        showProgressBar(false)
                    }
                    is AlbumList -> {
                        reloadAlbumList(it.albumList)
                        showProgressBar(false)
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let { viewModel.onSavedInstanceState(it) }
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