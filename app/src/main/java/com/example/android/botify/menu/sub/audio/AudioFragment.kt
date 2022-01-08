package com.example.android.botify.menu.sub.audio

import android.app.Activity
import android.content.ComponentName
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.android.botify.R
import com.example.android.botify.databinding.FragmentAudioBinding

class AudioFragment : Fragment()/*, AudioListener*/ {
    private val LOG_TAG = this::class.java.simpleName
/*    // audio adapter
    private lateinit var audioAdapter: AudioAdapter
    private val audioList = audioList()
*/
    // binding
    private lateinit var binding: FragmentAudioBinding

    private lateinit var mediaBrowser: MediaBrowserCompat
//    private lateinit var volumeControlStream: AudioManager.AudioPlaybackCallback

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            mediaBrowser.sessionToken.also { token ->
                val audioController = MediaControllerCompat(
                    context,
                    token
                )

                MediaControllerCompat.setMediaController(context as Activity, audioController)
            }

            buildTransportProtocols()
        }

        override fun onConnectionSuspended() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)
//            super.onConnectionSuspended()
        }

        override fun onConnectionFailed() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)
//            super.onConnectionFailed()
        }
    }

    private var controllerCallbacks = object : MediaControllerCompat.Callback() {

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            super.onMetadataChanged(metadata)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            super.onPlaybackStateChanged(state)
        }

        override fun onSessionDestroyed() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            mediaBrowser.disconnect()
            // maybe schedule a reconnection using a new MediaBrowser instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false)

        mediaBrowser = MediaBrowserCompat(
            context,
            context?.let { ComponentName(it, AudioService::class.java) },
            connectionCallbacks,
            null //optional Bundle
        )

        Log.i(LOG_TAG, "$methodName + audioBrowser")

/*        val recyclerView = binding.audioList
        audioAdapter = AudioAdapter(this, audioList, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = audioAdapter*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onViewCreated(view, savedInstanceState)

        binding.buttonAudioToMain.setOnClickListener {
            findNavController().navigate(R.id.action_AudioFragment_to_MainFragment)
        }
    }

    private fun buildTransportProtocols() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        val mediaController = MediaControllerCompat.getMediaController(context as Activity)

        // Grab the view for the play/pause button
        binding.displayAudioText.setOnClickListener {

                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly

            Log.i(LOG_TAG, "$methodName + sOCL")

            val pbState = mediaController.playbackState.state
            Log.i(LOG_TAG, "$methodName mC pS?: " + pbState.toString())

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
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onStart()

        mediaBrowser.connect()
    }

    override fun onResume() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onResume()
//        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onStop() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onStop()

        MediaControllerCompat.getMediaController(context as Activity)?.unregisterCallback(controllerCallbacks)

    }

    override fun onDestroy() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onDestroy()
    }
}
