package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.MediaLifecycleObserver
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.ActivityAppBinding
import ru.netology.nmedia.adapter.TrackAdapter
import ru.netology.nmedia.model.Track
import ru.netology.nmedia.viewmodel.MusicViewModel

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    private lateinit var mediaObserver: MediaLifecycleObserver
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaObserver = MediaLifecycleObserver()

        val adapter = TrackAdapter(object : OnInteractionListener {
            override fun onClick(track: Track) {
                val url = "${BuildConfig.BASE_URL}${track.file}"

                if (viewModel.curTrackId.value != track.id) {
                    mediaObserver.onStop()
                }

                if (mediaObserver.player == null || mediaObserver.player?.isPlaying!!) {
                    mediaObserver.onPause()
                } else {
                    if (viewModel.curTrackId.value != track.id) {
                        mediaObserver.apply {
                            player?.setDataSource(url)
                            player?.prepare()
                        }.play()
                    } else
                        mediaObserver.play()
                }
                viewModel.play(track.id)
            }

        })
        binding.trackList.adapter = adapter
        viewModel.data.observe(this) { state ->
            adapter.submitList(state.album.tracks)
            binding.albumTitle.text = state.album.title
            binding.artist.text = state.album.artist
            binding.published.text = state.album.published
            binding.genre.text = state.album.genre
            binding.commonButton.icon = AppCompatResources.getDrawable(
                binding.root.context,
                if (viewModel.isPlaying())
                    R.drawable.ic_baseline_pause_circle_filled_24 else
                    R.drawable.ic_baseline_play_circle_filled_24
            )
        }

        binding.commonButton.setOnClickListener {
            if (mediaObserver.player != null) {
                if (mediaObserver.player!!.isPlaying) {
                    mediaObserver.onPause()
                } else {
                    mediaObserver.play()
                }
                viewModel.curTrackId.value?.let { curTrackId ->
                    viewModel.play(curTrackId)
                }
            }
        }

        mediaObserver.player?.setOnCompletionListener {
            mediaObserver.onStop()
            viewModel.data.value?.album?.tracks?.let { tracks ->
                tracks.indexOfFirst { track ->
                    viewModel.curTrackId.value === track.id
                }.let { index ->
                    val nextTrack = tracks[if (index == (tracks.size - 1)) 0 else index + 1]
                    mediaObserver.apply {
                        player?.setDataSource("${BuildConfig.BASE_URL}${nextTrack.file}")
                        player?.prepare()
                    }.play()
                    viewModel.play(nextTrack.id)
                }
            }
        }

        lifecycle.addObserver(mediaObserver)
    }
}



