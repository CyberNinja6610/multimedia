package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.databinding.TrackBinding
import ru.netology.nmedia.MediaLifecycleObserver
import ru.netology.nmedia.model.Track

class TrackViewHolder(
    private val mediaObserver: MediaLifecycleObserver,
    private val binding: TrackBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(track: Track) {
        binding.apply {
            trackName.text = track.file
            stateButton.setOnClickListener {
                val url = "${BuildConfig.BASE_URL}${track.file}"

                if (mediaObserver.curUrl.isNotBlank() && mediaObserver.curUrl != url) {
                    mediaObserver.onStop()
                }

                if (mediaObserver.player == null || mediaObserver.player?.isPlaying!!) {
                    mediaObserver.onPause()
                } else {
                    if (mediaObserver.curUrl != url) {
                        mediaObserver.apply {
                            player?.setDataSource(url)
                            player?.prepare()
                            curUrl = url
                            trackButton = stateButton
                        }.play()
                    } else
                        mediaObserver.play()
                }
            }

            val url = "${BuildConfig.BASE_URL}${track.file}"
            if (mediaObserver.curUrl.isBlank()) {
                mediaObserver.apply {
                    player?.setDataSource(url)
                    player?.prepare()
                    trackButton = stateButton
                    curUrl = url
                }
            }
        }
    }


}