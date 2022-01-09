package com.example.android.botify.menu.sub.audio

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.media.MediaBrowserServiceCompat
import com.example.android.botify.R

private const val MEDIA_ROOT_ID = "root_id"

class AudioService : MediaBrowserServiceCompat() {
    private val LOG_TAG = this::class.java.simpleName

    private var mediaPlayer: MediaPlayer? = null
    private var mediaSession: MediaSessionCompat? = null

    private lateinit var audioManager: AudioManager
    private lateinit var audioFocusRequest: AudioFocusRequest
//    private lateinit var audioFocusChangeListener: AudioManager.OnAudioFocusChangeListener
    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    private lateinit var metaDataBuilder: MediaMetadataCompat.Builder



    private var audioServiceCallbacks = object : MediaSessionCompat.Callback() {
        private var currentPosition: Long? = null

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

/*            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
                setAudioAttributes(AudioAttributes.Builder().run {
                    setUsage((AudioAttributes.USAGE_MEDIA))
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                build()
            }*/

            val result = audioManager.requestAudioFocus(audioFocusRequest)
            Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + $result")

            val state = stateBuilder.build().state

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.i(LOG_TAG, "$methodName mP: $mediaPlayer")
                Log.i(LOG_TAG, "$methodName aM: $audioManager")
                Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + $result")

                startService(Intent(this@AudioService, MediaBrowserServiceCompat::class.java))

                mediaSession?.isActive = true

                if (state == PlaybackStateCompat.STATE_NONE || state == PlaybackStateCompat.STATE_STOPPED) {
                    mediaPlayer?.setDataSource(this@AudioService, uri!!)
                    mediaPlayer?.prepare()
                }


                mediaPlayer?.start()

                currentPosition = mediaPlayer?.currentPosition?.toLong()

                stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, currentPosition!!, 1F)
                Log.i(LOG_TAG, "$methodName: pB state: " + stateBuilder.build().state.toString())

                mediaSession?.setPlaybackState(stateBuilder.build())
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPlay() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            val state = stateBuilder.build().state
            Log.i(LOG_TAG, "$methodName iniState: " + state)

            if (state == PlaybackStateCompat.STATE_PAUSED) {
                startService(Intent(this@AudioService, MediaBrowserServiceCompat::class.java))
                Log.i(LOG_TAG, "$methodName mP: $mediaPlayer")
                Log.i(LOG_TAG, "$methodName aM: $audioManager")
                Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + " + audioManager.requestAudioFocus(audioFocusRequest))

                mediaSession?.isActive = true

                mediaPlayer?.start()


                currentPosition = mediaPlayer?.currentPosition?.toLong()

                stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, currentPosition!!, 1F)
                Log.i(LOG_TAG, "$methodName: pB state: " + stateBuilder.build().state.toString())

                mediaSession?.setPlaybackState(stateBuilder.build())
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPause() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)
            Log.i(LOG_TAG, "$methodName mP: $mediaPlayer")
            Log.i(LOG_TAG, "$methodName aM: $audioManager")
            Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + " + audioManager.requestAudioFocus(audioFocusRequest))


            mediaPlayer?.pause()

            currentPosition = mediaPlayer?.currentPosition?.toLong()

            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, currentPosition!!, 1F)
            Log.i(LOG_TAG, "$methodName: pB state: " + stateBuilder.build().state.toString())

            mediaSession?.setPlaybackState(stateBuilder.build())
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onStop() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            Log.i(LOG_TAG, "$methodName mP: $mediaPlayer")
            Log.i(LOG_TAG, "$methodName aM: $audioManager")
            Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + " + audioManager.requestAudioFocus(audioFocusRequest))

            mediaPlayer?.stop()
            mediaPlayer?.reset()


            stateBuilder.setState(PlaybackStateCompat.STATE_STOPPED, 0L, 1F)
            Log.i(LOG_TAG, "$methodName: pB state: " + stateBuilder.build().state.toString())

            mediaSession?.setPlaybackState(stateBuilder.build())

            mediaSession?.isActive = false

            audioManager.abandonAudioFocusRequest(audioFocusRequest)
            Log.i(LOG_TAG, "$methodName ab-aFR: $audioFocusRequest")
            Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + " + audioManager.requestAudioFocus(audioFocusRequest))

            stopSelf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onCreate()

        mediaPlayer = MediaPlayer()
        Log.i(LOG_TAG, "$methodName mP: $mediaPlayer")

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        Log.i(LOG_TAG, "$methodName aM: $audioManager")

        audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
            setAudioAttributes(AudioAttributes.Builder(). run {
                setUsage((AudioAttributes.USAGE_MEDIA))
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                build()
            })
            build()
        }

        Log.i(LOG_TAG, "$methodName aFR: $audioFocusRequest + " + audioManager.requestAudioFocus(audioFocusRequest))

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
                        or PlaybackStateCompat.ACTION_PAUSE
                        or PlaybackStateCompat.ACTION_STOP
                )
            setPlaybackState(stateBuilder.build())

            // dunno yet what to do with this
            metaDataBuilder = MediaMetadataCompat.Builder()

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

        return BrowserRoot(MEDIA_ROOT_ID, null)
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

        mediaPlayer?.reset()
        mediaPlayer?.release()

/*        mediaPlayer?.reset()
        mediaPlayer?.release()*/
        super.onDestroy()
    }
}
