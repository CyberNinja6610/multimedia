package ru.netology.nmedia

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.button.MaterialButton
import ru.netology.nmedia.R

class MediaLifecycleObserver(private val context: Context) : LifecycleObserver {
    var player: MediaPlayer? = MediaPlayer()
    var curUrl: String = ""
    var commonButton: MaterialButton? = null
    var trackButton: MaterialButton? = null

    fun play() {

        player?.start()
        trackButton?.icon = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_baseline_pause_circle_filled_24
        )
        commonButton?.icon = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_baseline_pause_circle_filled_24
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        player?.pause()
        trackButton?.icon = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_baseline_play_circle_filled_24
        )
        commonButton?.icon = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_baseline_play_circle_filled_24
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        player?.stop()
        player?.reset()
        trackButton?.icon = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_baseline_play_circle_filled_24
        )
        commonButton?.icon = AppCompatResources.getDrawable(
            context,
            R.drawable.ic_baseline_play_circle_filled_24
        )
    }

}