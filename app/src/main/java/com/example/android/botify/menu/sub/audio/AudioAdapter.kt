package com.example.android.botify.menu.sub.audio

import android.content.ContentResolver
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.botify.R

class AudioAdapter(private val context: AudioFragment, private val audioList: List<Audio>, private val audioListener: AudioListener) :
    RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.audio_item)
        private var currentAudio: Audio? = null

        init {
            view.setOnClickListener {
//                val uri  =  Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority("com.example.android.botify").path(adapterPosition.toString()).build()
                audioListener.onAudioClickListener(adapterPosition)
            }
        }

        fun bind(audio: Audio) {
            currentAudio = audio
            textView.text = audio.name
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AudioViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.audio_item_view, viewGroup, false)

        return AudioViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(audioViewHolder: AudioViewHolder, position: Int) {
        audioViewHolder.bind(audioList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = audioList.size

}
