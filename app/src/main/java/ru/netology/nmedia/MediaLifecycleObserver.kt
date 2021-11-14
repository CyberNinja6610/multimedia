package ru.netology.nmedia

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MediaLifecycleObserver : LifecycleObserver {
    var player: MediaPlayer? = MediaPlayer()

    fun play() {
        player?.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        player?.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        player?.stop()
        player?.reset()
    }

}