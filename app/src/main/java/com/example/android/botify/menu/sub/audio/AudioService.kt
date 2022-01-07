package com.example.android.botify.menu.sub.audio

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat

private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"

class AudioService : MediaBrowserServiceCompat() {

    private var audioPlayer: MediaPlayer? = null
    private var audioSession: MediaSessionCompat? = null
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    private var audioServiceCallbacks = object : MediaSessionCompat.Callback() {

/*        override fun onPlay() {
            val myUri: Uri = ()
            audioPlayer?.setDataSource(this@AudioService, myUri).prepare().start()
        }*/

        override fun onPause() {
            audioPlayer?.pause()
        }

        override fun onStop() {
            audioPlayer?.stop()
        }
    }

    override fun onCreate() {
        super.onCreate()

        audioPlayer = MediaPlayer()

        // Create a MediaSessionCompat
        audioSession = MediaSessionCompat(baseContext, "AudioServiceTAG").apply {

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
        return BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }
}
