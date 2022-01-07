package com.example.android.botify.menu.sub.audio

import android.app.Activity
import android.content.ComponentName
import android.content.ContentResolver
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.botify.R
import com.example.android.botify.databinding.FragmentAudioBinding

class AudioFragment : Fragment()/*, AudioListener*/ {
/*    // audio adapter
    private lateinit var audioAdapter: AudioAdapter
    private val audioList = audioList()
*/

    // binding
    private lateinit var binding: FragmentAudioBinding

    private lateinit var audioBrowser: MediaBrowserCompat
//    private lateinit var volumeControlStream: AudioManager.AudioPlaybackCallback

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
//            super.onConnected()
            audioBrowser.sessionToken.also { token ->
                val audioController = MediaControllerCompat(
                    context,
                    token
                )

                MediaControllerCompat.setMediaController(context as Activity, audioController)
            }

            buildTransportProtocols()
        }

        override fun onConnectionSuspended() {
//            super.onConnectionSuspended()
        }

        override fun onConnectionFailed() {
//            super.onConnectionFailed()
        }
    }

    private var controllerCallbacks = object : MediaControllerCompat.Callback() {

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
        }

        override fun onSessionDestroyed() {
            audioBrowser.disconnect()
            // maybe schedule a reconnection using a new MediaBrowser instance
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false)

        audioBrowser = MediaBrowserCompat(
            context,
            context?.let { ComponentName(it, AudioService::class.java) },
            connectionCallbacks,
            null //optional Bundle
        )

/*        val recyclerView = binding.audioList
        audioAdapter = AudioAdapter(this, audioList, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = audioAdapter*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAudioToMain.setOnClickListener {
            findNavController().navigate(R.id.action_AudioFragment_to_MainFragment)
        }
    }

    private fun buildTransportProtocols() {
        val mediaController = MediaControllerCompat.getMediaController(context as Activity)
        // Grab the view for the play/pause button
        binding.displayAudioText.setOnClickListener {

                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly

                val pbState = mediaController.playbackState.state
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    mediaController.transportControls.pause()
                } else {
                    mediaController.transportControls.play()
                }
            }

        // Display the initial state
        val metadata = mediaController.metadata
        val pbState = mediaController.playbackState

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallbacks)
    }

    override fun onStart() {
        super.onStart()
        audioBrowser.connect()
    }

    override fun onResume() {
        super.onResume()
//        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onStop() {
        Log.i("AudioFragment", "onStop Called")
        super.onStop()
        MediaControllerCompat.getMediaController(context as Activity)?.unregisterCallback(controllerCallbacks)

    }

    override fun onDestroy() {
        Log.i("AudioFragment: ", "onDestroy called")
        super.onDestroy()
    }
}
