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

    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("MainFragment", "onCreateView")
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToAudioSubmenu.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_AudioFragment)
        }

        binding.buttonToVideoSubmenu.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_VideoFragment)
        }

        binding.buttonToImagesSubmenu.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_ImageFragment)
        }

        binding.buttonToAboutSubmenu.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_AboutFragment)
        }
    }

    override fun onDestroy() {
        Log.i("MainFragment: ", "onDestroy")
        super.onDestroy()
    }
}