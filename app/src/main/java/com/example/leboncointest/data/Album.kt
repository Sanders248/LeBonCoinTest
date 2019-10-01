package com.example.leboncointest.data

data class Album(val title: String?, val url: String?)

data class AlbumResponse(val albumId: String?, val id: Int, val title: String?, val url: String?, val thumbnailUrl: String?)