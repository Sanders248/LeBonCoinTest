package com.example.leboncointest.tools

import android.content.Context
import android.preference.PreferenceManager
import com.example.leboncointest.data.Album
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

const val KEY_ALBUM_LIST = "ALBUM_LIST"

/**
 * this class manage the sharedPreference of the app
 */
class SharedPreferenceManager(var context: Context?) {
    fun putAlbumList(albumList: List<Album>) {
        val gson = GsonBuilder().create()
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val json = gson.toJson(albumList)
        val editor = preferences.edit()

        editor.putString(KEY_ALBUM_LIST, json)
        editor.apply()
    }

    fun getAlbumList(): List<Album> {
        val gson = GsonBuilder().create()
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val json = preferences.getString(KEY_ALBUM_LIST, null)
        val listType = object : TypeToken<ArrayList<Album>>() {}.type

        return gson.fromJson(json, listType) ?: emptyList()
    }
}