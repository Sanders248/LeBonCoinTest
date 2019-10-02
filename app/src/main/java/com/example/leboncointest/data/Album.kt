package com.example.leboncointest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AlbumResponse(val albumId: String?, val id: Int, val title: String?, val url: String?, val thumbnailUrl: String?)

@Parcelize
data class Album(val title: String, val url: String) : Parcelable
