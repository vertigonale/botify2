package com.example.android.botify.menu.main

import android.app.Activity
import android.content.ComponentName
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.example.android.botify.R
import com.example.android.botify.databinding.ActivityMainBinding
import com.example.android.botify.menu.sub.audio.AudioService

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = this::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
//    private lateinit var menuTitle: String

    private lateinit var mediaBrowser: MediaBrowserCompat

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

            mediaBrowser.sessionToken.also { token ->
                val audioController = MediaControllerCompat(
                    this@MainActivity,
                    token
                )

                MediaControllerCompat.setMediaController(this@MainActivity, audioController)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

//        binding.bottomAppBar.menu[R.id.menu_name].title.

        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, AudioService::class.java),
            connectionCallbacks,
            null //optional Bundle
        )

        Log.i(LOG_TAG, "$methodName + mediaBrowser")
    }

    //TODO check if necessary (app runs without crashes when commented out)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

/*        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
            Log.i(LOG_TAG, "$methodName + MenuInflated?")*/

        menuInflater.inflate(R.menu.bottom_app_bar, menu)
        Log.i(LOG_TAG, "$methodName + bottom_app_bar?")

/*        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.play_pause -> {

                    // TODO play pause
                    val mediaController = getMainMediaController()

                    val pbStateTest = mediaController.playbackState.state
                    Log.i(LOG_TAG, "$methodName mC pS?: " + pbStateTest.toString())

                    if (pbStateTest == PlaybackStateCompat.STATE_PLAYING) {
                        Log.i(LOG_TAG, "$methodName + sOCL if 1: " + pbStateTest.toString())
                        mediaController.transportControls.pause()
                        Log.i(LOG_TAG, "$methodName + sOCL if 2: " + pbStateTest.toString())
                    } else {
                        Log.i(LOG_TAG, "$methodName + sOCL else 1: " + pbStateTest.toString())
                        mediaController.transportControls.play()
                        Log.i(LOG_TAG, "$methodName + sOCL else 2: " + pbStateTest.toString())
                    }
                    // TODO maybe a toast telling there is no audio yet selected
//                    else {}

                    true
                }
                R.id.more -> {
                    // TODO more
                    true
                }
                else -> false
            }
        }*/

/*        binding.bottomAppBar.setOnMenuItemClickListener { menuI
        }*/

        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun buildTransportProtocols() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        val mediaController = getMainMediaController()

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.play_pause -> {

                    val pbStateTest = mediaController.playbackState.state
                    Log.i(LOG_TAG, "$methodName mC pS?: " + pbStateTest.toString())

                    if (pbStateTest == PlaybackStateCompat.STATE_PLAYING) {
                        Log.i(LOG_TAG, "$methodName + sOCL if 1: " + pbStateTest.toString())
                        mediaController.transportControls.pause()
                        Log.i(LOG_TAG, "$methodName + sOCL if 2: " + pbStateTest.toString())
                    } else {
                        Log.i(LOG_TAG, "$methodName + sOCL else 1: " + pbStateTest.toString())
                        mediaController.transportControls.play()
                        Log.i(LOG_TAG, "$methodName + sOCL else 2: " + pbStateTest.toString())
                    }
                    // TODO maybe a toast telling there is no audio yet selected
//                    else {}

                    true
                }
                R.id.more -> {
                    // TODO more
                    true
                }
                else -> false
            }
        }

        // Display the initial state
        val metadata = mediaController.metadata
        val pbState = mediaController.playbackState

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallbacks)
    }

    fun getMainMediaController() : MediaControllerCompat {

        return MediaControllerCompat.getMediaController(this)
    }

    override fun onStart() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onStart()
        if (!mediaBrowser.isConnected) {
            Log.i(LOG_TAG, "$methodName mB con? " + mediaBrowser.isConnected)

            mediaBrowser.connect()
            Log.i(LOG_TAG, "$methodName mB con? " + mediaBrowser.isConnected)
        }
/*        val mediaController = getMainMediaController()
        Log.i(LOG_TAG, "$methodName + $mediaController")*/
    }

    override fun onStop() {

        getMainMediaController().unregisterCallback(controllerCallbacks)

        super.onStop()
    }

    override fun onDestroy() {
        val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

        val delete: Boolean = this.cacheDir.deleteRecursively()
        Log.i(LOG_TAG, "$methodName + Delete? $delete")

        super.onDestroy()
    }
}