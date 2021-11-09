package ru.netology.nmedia.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import ru.netology.nmedia.ui.api.Api
import ru.netology.nmedia.ui.model.FeedModel

class MusicViewModel : ViewModel() {
    private val _data = MutableLiveData<FeedModel>()
    val data: LiveData<FeedModel>
        get() = _data

    init {
        loadTracks()
    }

    private fun loadTracks() = viewModelScope.launch {
        try {
            val response = Api.service.loadAlbum()
            if (response.isSuccessful) {
                _data.value = FeedModel(
                    album = response.body() ?: throw Exception("Не удалось загрузить альбом"),
                )
            }
        } catch (e: Exception) {
            Log.d("MUSIC", "loadTracks: " + e.message)
        }
    }
}