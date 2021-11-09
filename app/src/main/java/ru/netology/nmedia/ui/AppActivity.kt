package ru.netology.nmedia.ui

import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityAppBinding
import ru.netology.nmedia.ui.adapter.TrackAdapter
import ru.netology.nmedia.ui.viewmodel.MusicViewModel

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    private lateinit var mediaObserver: MediaLifecycleObserver
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaObserver = MediaLifecycleObserver(binding.root.context)

        val adapter = TrackAdapter(mediaObserver)
        binding.trackList.adapter = adapter
        viewModel.data.observe(this) { state ->
            adapter.submitList(state.album.tracks)
            binding.albumTitle.text = state.album.title
            binding.artist.text = state.album.artist
            binding.published.text = state.album.published
            binding.genre.text = state.album.genre
        }

        binding.commonButton.setOnClickListener {
            if (mediaObserver.player != null) {
                var a = mediaObserver
                if (mediaObserver.player!!.isPlaying) {
                    mediaObserver.onPause()
                } else {
                    mediaObserver.play()
                }
            }
        }
        mediaObserver.bigButton = binding.commonButton

        lifecycle.addObserver(mediaObserver)
    }
}



