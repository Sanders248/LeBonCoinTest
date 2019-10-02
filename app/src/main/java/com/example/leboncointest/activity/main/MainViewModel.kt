package com.example.leboncointest.activity.main

import android.app.Application
import android.arch.lifecycle.*
import android.os.Bundle
import android.util.Log
import com.example.leboncointest.data.Album
import com.example.leboncointest.tools.CommonUtils
import com.example.leboncointest.tools.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(savedInstanceState: Bundle?, application: Application) : AndroidViewModel(application) {
    private val TAG = "MainViewModel"
    private val KEY_RESTORABLE_DATA = "KEY_RESTORABLE_DATA"

    private var disposable: Disposable? = null
    private val useCaseMain = UseCaseMain()

    private val _albumList = MutableLiveData<Event<AlbumState>>()
    val albumList: LiveData<Event<AlbumState>> get() = _albumList

    init {
        val restorableData = savedInstanceState?.getParcelableArrayList<Album>(KEY_RESTORABLE_DATA)
        _albumList.postValue(Event(AlbumState.Loading, true))

        when {
            restorableData != null -> _albumList.postValue(Event(AlbumState.AlbumList(restorableData)))
            isNetworkAvailable() -> getAlbumListFromApi()
            else -> getAlbumListFromPreferences()
        }
    }

    private fun getAlbumListFromApi() {
        disposable = useCaseMain.getAlbumListFromApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { albumList ->
                    useCaseMain.putAlbumListIntoPreferences(getApplication(), albumList)
                    _albumList.postValue(Event(AlbumState.AlbumList(albumList)))
                },
                onError = {
                    Log.e(TAG, "getAlbumList onError: $it")
                    _albumList.postValue(Event(AlbumState.Failure, true))
                }
            )
    }

    private fun getAlbumListFromPreferences() {
        val albumList = useCaseMain.getAlbumListFromPreferences(getApplication())
        _albumList.postValue(Event(AlbumState.AlbumList(albumList)))
    }

    private fun isNetworkAvailable(): Boolean = CommonUtils.isNetworkAvailable(getApplication())

    fun onSavedInstanceState(outState: Bundle) {
        albumList.value?.getContent()?.let {
            if (it is AlbumState.AlbumList) {
                outState.putParcelableArrayList(
                    KEY_RESTORABLE_DATA,
                    ArrayList(it.albumList.take(20))
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.apply { if (!isDisposed) dispose() }
    }

    sealed class AlbumState {
        data class AlbumList(val albumList: List<Album>) : AlbumState()
        object Loading : AlbumState()
        object Failure : AlbumState()
    }

    @Suppress("UNCHECKED_CAST")
    class BaseViewModelFactory(private val application: Application, private val savedInstanceState: Bundle? = null) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(savedInstanceState, application) as T
        }
    }
}