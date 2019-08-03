package com.example.leboncointest.activity.main

import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import io.mockk.every
import io.mockk.mockkObject
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins


class MainPresenterTest {

    private lateinit var presenter: MainPresenter

    @Mock
    lateinit var view: MainContract.view

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)

        presenter = MainPresenter(null).apply { setView(view) }
    }

    @Test
    fun test() {
        val albumList = listOf(Album("albumId", 0, "title", "url", "thumbnailUrl"))

        mockkObject(AlbumApiManager)
        every { AlbumApiManager.albumApi.getAlbumList() } returns Single.just(albumList)

        `when`(view.isNetworkAvailable()).thenReturn(true)

        presenter.showListView()

        verify(view).reloadAlbumList(albumList)
    }
}