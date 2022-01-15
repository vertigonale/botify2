package com.example.android.botify.menu.sub.audio

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.botify.R

class AudioAdapter(private val context: AudioFragment, private val audioList: List<Audio>, private val audioListener: AudioListener) :
    RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {
    private val LOG_TAG = this::class.java.simpleName

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val LOG_TAG = this::class.java.simpleName
        private val textView: TextView = view.findViewById(R.id.audio_item)
        private var currentAudio: Audio? = null

        init {

            view.setOnClickListener {
                audioListener.onAudioClickListener(adapterPosition)
            }
        }

        fun bind(audio: Audio) {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            currentAudio = audio
            textView.text = audio.name
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AudioViewHolder {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.audio_item_view, viewGroup, false)

        return AudioViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(audioViewHolder: AudioViewHolder, position: Int) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        audioViewHolder.bind(audioList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = audioList.size

}
