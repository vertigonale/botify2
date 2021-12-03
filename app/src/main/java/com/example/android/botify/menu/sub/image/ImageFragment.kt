package com.example.android.botify.menu.sub.image

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.botify.R
import com.example.android.botify.databinding.FragmentImageBinding

class ImageFragment : Fragment(), ImageListener {

    // variables
    private var imageId: Int? = R.drawable.xmchn2_entry

    // images
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = imagesList()

    // binding
    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("ImageFragment", "onCreateView called")
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false)

        val recyclerView = binding.imageList
        imageAdapter = ImageAdapter(imageList, this)
        val layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imageAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("ImageFragment: ", "onViewCreated")

        binding.displayImage.setImageResource(imageId!!)

        binding.displayImage.setOnClickListener {
            findNavController().navigate(ImageFragmentDirections.actionImageFragmentToImageDetailFragment(imageId!!))
        }

        binding.buttonImageToMain.setOnClickListener {
            findNavController().navigate(R.id.action_ImageFragment_to_MainFragment)
        }
    }

    override fun onImageClickListener(image: Image) {
        imageId = image.image
        image.image?.let { binding.displayImage.setImageResource(it) }
        binding.displayImageText.text = image.name
    }

    override fun onStart() {
        Log.i("ImageFragment: ", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i("ImageFragment: ", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.i("ImageFragment: ", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i("ImageFragment: ", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.i("ImageFragment: ", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i("ImageFragment", "onDestroy called")
        var delete: Boolean? = context?.cacheDir?.delete()
        Log.i("ImageFrag Cache Del?", delete?.toString()!!)
        super.onDestroy()
    }
}
