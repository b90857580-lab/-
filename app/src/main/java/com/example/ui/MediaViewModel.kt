package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.MediaRepository
import com.example.data.Song
import com.example.player.PlaybackManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaViewModel(
    private val repository: MediaRepository,
    val playbackManager: PlaybackManager
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs.asStateFlow()

    init {
        loadSongs()
    }

    fun loadSongs() {
        viewModelScope.launch {
            _songs.value = repository.getLocalSongs()
        }
    }

    override fun onCleared() {
        super.onCleared()
        playbackManager.release()
    }
}

class MediaViewModelFactory(
    private val repository: MediaRepository,
    private val playbackManager: PlaybackManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MediaViewModel(repository, playbackManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
