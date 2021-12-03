package com.example.android.botify.menu.sub.image

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.botify.R
import com.example.android.botify.databinding.FragmentImageDetailBinding

class ImageDetailFragment : Fragment() {

    private lateinit var binding: FragmentImageDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("ImageDetailFragment: ", "onCreateView")
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_detail, container, false)

        val imageView: ImageView = binding.imageToDisplay
        val imageId: Int = arguments?.let { ImageDetailFragmentArgs.fromBundle(it).imageDetailFragmentArgs }!!
        imageView.setImageResource(imageId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("ImageDetailFragment: ", "onViewCreated")
        binding.imageToDisplay.setOnClickListener {
            findNavController().navigate(R.id.action_imageDetailFragment_to_imageFragment)
        }
    }

    override fun onStart() {
        Log.i("ImageDetailFragment: ", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i("ImageDetailFragment: ", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.i("ImageDetailFragment: ", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i("ImageDetailFragment: ", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.i("ImageDetailFragment: ", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i("ImageDetailFragment", "onDestroy called")
        super.onDestroy()
    }
}