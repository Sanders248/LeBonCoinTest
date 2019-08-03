package com.example.leboncointest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.leboncointest.data.Album
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity display a list of Album
 * It show the image (from url) and the title of each Album
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var viewAdapter: ImageListAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        initListView()

        presenter.showListView()
    }

    override fun onPause() {
        super.onPause()
        presenter.disposable?.dispose()
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

    fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun reloadAlbumList(albumList: List<Album>) {
        viewAdapter.albumList = albumList
        viewAdapter.notifyDataSetChanged()
    }

}
