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
//LOG
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
//LOG
        override fun onConnectionSuspended() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)
//            super.onConnectionSuspended()
        }
//LOG
        override fun onConnectionFailed() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)
//            super.onConnectionFailed()
        }
    }

    private var controllerCallbacks = object : MediaControllerCompat.Callback() {
//LOG
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            super.onMetadataChanged(metadata)
        }
//LOG
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            super.onPlaybackStateChanged(state)
        }
//LOG
        override fun onSessionDestroyed() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            mediaBrowser.disconnect()
            // maybe schedule a reconnection using a new MediaBrowser instance
        }
    }

//LOG
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
//LOG
        Log.i(LOG_TAG, "$methodName + audioBrowser")

/*        val recyclerView = binding.audioList
        audioAdapter = AudioAdapter(this, audioList, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = audioAdapter*/

        return binding.root
    }
//LOG
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onViewCreated(view, savedInstanceState)

        binding.buttonAudioToMain.setOnClickListener {
            findNavController().navigate(R.id.action_AudioFragment_to_MainFragment)
        }
    }
//LOG
    private fun buildTransportProtocols() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        val mediaController = MediaControllerCompat.getMediaController(context as Activity)
//LOG
        Log.i(LOG_TAG, "$methodName mC: " + mediaController.toString())
        Log.i(LOG_TAG, "$methodName mC: " + mediaController.sessionToken)
        Log.i(LOG_TAG, "$methodName mC S?: " + mediaController.isSessionReady().toString())

        // Grab the view for the play/pause button
        binding.displayAudioText.setOnClickListener {

                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly
//LOG
            Log.i(LOG_TAG, "$methodName + sOCL")
//LOG
            val pbState = mediaController.playbackState.state
            Log.i(LOG_TAG, "$methodName mC pS?: " + pbState.toString())
//LOG
            if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                Log.i(LOG_TAG, "if1" + pbState.toString())
                mediaController.transportControls.pause()
                Log.i(LOG_TAG, "if2" + pbState.toString())
            } else {
                Log.i(LOG_TAG, "else 1: " + pbState.toString())
                mediaController.transportControls.play()
                Log.i(LOG_TAG, "else2 : " + pbState.toString())
            }
        }

        // Display the initial state
        val metadata = mediaController.metadata
        val pbState = mediaController.playbackState

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallbacks)
    }
//LOG
    override fun onStart() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onStart()

        mediaBrowser.connect()
    }
//LOG
    override fun onResume() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onResume()
//        volumeControlStream = AudioManager.STREAM_MUSIC
    }
//LOG
    override fun onStop() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onStop()

        MediaControllerCompat.getMediaController(context as Activity)?.unregisterCallback(controllerCallbacks)

    }
//LOG
    override fun onDestroy() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onDestroy()
    }
}
