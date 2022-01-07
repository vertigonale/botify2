package com.example.android.botify.menu.sub.audio

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.drm.DrmStore
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import com.example.android.botify.R

private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"

class AudioService : MediaBrowserServiceCompat() {
    private val LOG_TAG = this::class.java.simpleName

    private var mediaPlayer: MediaPlayer? = null
    private var mediaSession: MediaSessionCompat? = null
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    private var audioServiceCallbacks = object : MediaSessionCompat.Callback() {

        override fun onPlay() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            val uri: Uri =  Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority("com.example.android.botify").path(R.raw.winter_path_of_liars.toString()).build()

/*            Log.i(LOG_TAG, "$methodName: " + mediaSession?.isActive)
            mediaSession?.isActive = true*/
            Log.i(LOG_TAG, "$methodName: " + mediaSession?.isActive)

            Log.i(LOG_TAG, "$methodName: $mediaPlayer")
            Log.i(LOG_TAG, "$methodName: $mediaSession")
            if (stateBuilder.build().state == PlaybackStateCompat.STATE_NONE) {
                mediaPlayer?.setDataSource(this@AudioService, uri)
                mediaPlayer?.prepare()
            }

            mediaPlayer?.start()
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, 0L, 1F)
            Log.i(LOG_TAG, stateBuilder.build().state.toString())

            mediaSession?.setPlaybackState(stateBuilder.build())
//            stateBuilder.build()
            Log.i(LOG_TAG, "$methodName: " + mediaSession?.isActive)
        }

        override fun onPause() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            mediaPlayer?.pause()

            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, 0L, 1F)
            Log.i(LOG_TAG, stateBuilder.build().state.toString())

            mediaSession?.setPlaybackState(stateBuilder.build())
        }

        override fun onStop() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            mediaPlayer?.stop()
        }
    }

    override fun onCreate() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onCreate()

        mediaPlayer = MediaPlayer()

        // Create a MediaSessionCompat
        mediaSession = MediaSessionCompat(baseContext, "AudioServiceTAG").apply {
            Log.i(LOG_TAG, "$methodName + session")

            // Enable callbacks from MediaButtons and TransportControls
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY
                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(stateBuilder.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
            setCallback(audioServiceCallbacks)

            // Set the session's token so that client activities can communicate with it.
            setSessionToken(sessionToken)
        }
    }


    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        return BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

/*        mediaPlayer?.reset()
        mediaPlayer?.release()*/
        super.onDestroy()
    }
}
