package com.example.leboncointest.activity.main

import com.example.leboncointest.api.AlbumApiManager
import com.example.leboncointest.data.Album
import com.example.leboncointest.data.AlbumResponse
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
/*
    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test() {
        val albumResponseList = listOf(AlbumResponse("albumId", 0, "title", "url", "thumbnailUrl"))
        val albumList = listOf(Album("title", "url"))

        mockkObject(AlbumApiManager)
        every { AlbumApiManager.albumApi.getAlbumList() } returns Single.just(albumResponseList)

        `when`(.isNetworkAvailable()).thenReturn(true)

        verify(view).reloadAlbumList(albumList)
    }
*/

}