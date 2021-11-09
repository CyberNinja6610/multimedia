package ru.netology.nmedia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.databinding.TrackBinding
import ru.netology.nmedia.ui.MediaLifecycleObserver
import ru.netology.nmedia.ui.model.Track

class TrackAdapter(private val mediaObserver: MediaLifecycleObserver)
    : ListAdapter<Track, TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(mediaObserver, binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }

    override fun getItemViewType(position: Int): Int {
        return position;
    }
}
