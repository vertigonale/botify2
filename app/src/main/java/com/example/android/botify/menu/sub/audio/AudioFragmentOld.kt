package com.example.android.botify.menu.sub.audio
/*
import android.content.ContentResolver
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.botify.R
import com.example.android.botify.databinding.FragmentAudioBinding

class AudioFragmentOld : Fragment(), AudioListener {

    // media player
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable:Runnable
    //TODO what to use apart from handler (deprecated)
    private var handler: Handler = Handler()
    private var pause:Boolean = false

    // audio adapter
    private lateinit var audioAdapter: AudioAdapter
    private val audioList = audioList()

    // binding
    private lateinit var binding: FragmentAudioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false)

        val recyclerView = binding.audioList
        audioAdapter = AudioAdapter(this, audioList, this)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = audioAdapter
        mediaPlayer = MediaPlayer()
        Log.i("AudioFragment: ", "MediaPlayer instantiated")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAudioToMain.setOnClickListener {
            findNavController().navigate(R.id.action_AudioFragment_to_MainFragment)
        }

//        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//                if (b) {
//                    mediaPlayer!!.seekTo(i * 1000)
//                }
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar) {
//            }
//        })
    }

//    private fun initializeSeekBar() {
//        binding.seekBar.max = mediaPlayer!!.seconds
//
//        runnable = Runnable {
//            binding.seekBar.progress = mediaPlayer!!.currentSeconds
//
//            handler.postDelayed(runnable, 1000)
//        }
//        handler.postDelayed(runnable, 1000)
//    }
//
//    val MediaPlayer.seconds:Int
//        get() {
//            return this.duration / 1000
//        }
//
//    val MediaPlayer.currentSeconds:Int
//        get() {
//            return this.currentPosition/1000
//        }

    override fun onAudioClickListener(position: Int) {

        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
//            mediaPlayer.release()
            Log.i("AudioFragment", "mediaplayer released/reset")
        }

        var uri: String =  Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority("com.example.android.botify").path(audioList[position].audio.toString()).build().toString()

        binding.displayAudioText.text = audioList[position].name
        activity?.let { mediaPlayer!!.setDataSource(it, Uri.parse(uri)) }
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
//        initializeSeekBar()
    }

    override fun onStop() {
        Log.i("AudioFragment", "onStop Called")
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
        mediaPlayer!!.reset()
        var delete: Boolean? = context?.cacheDir?.delete()
        Log.i("Deleted?", delete?.toString()!!)
        super.onStop()

    }

    override fun onDestroy() {
        Log.i("AudioFragment: ", "onDestroy called")
        mediaPlayer!!.release()
        mediaPlayer = null
        super.onDestroy()
    }
}
*/
