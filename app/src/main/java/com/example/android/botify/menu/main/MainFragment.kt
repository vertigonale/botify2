package com.example.android.botify.menu.main

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.botify.R
import com.example.android.botify.databinding.FragmentMainBinding
import java.util.concurrent.Executor

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {
    private val LOG_TAG = this::class.java.simpleName

    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)


        super.onViewCreated(view, savedInstanceState)

        binding.buttonToAudioSubmenu.setOnClickListener {
            Log.i(LOG_TAG, "$methodName + sOCL A")

            findNavController().navigate(R.id.action_MainFragment_to_AudioFragment)
        }

        binding.buttonToVideoSubmenu.setOnClickListener {
            Log.i(LOG_TAG, "$methodName + sOCL V")
            findNavController().navigate(R.id.action_MainFragment_to_VideoFragment)
        }

        binding.buttonToImagesSubmenu.setOnClickListener {
            Log.i(LOG_TAG, "$methodName + sOCL I")
            findNavController().navigate(R.id.action_MainFragment_to_ImageFragment)
        }

        binding.buttonToAboutSubmenu.setOnClickListener {
            Log.i(LOG_TAG, "$methodName + sOCL a")
            findNavController().navigate(R.id.action_MainFragment_to_AboutFragment)
        }
    }

    override fun onDestroy() {
        Log.i("MainFragment: ", "onDestroy")
        super.onDestroy()
    }
}