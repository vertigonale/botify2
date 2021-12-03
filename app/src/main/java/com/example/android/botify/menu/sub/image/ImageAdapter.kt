package com.example.android.botify.menu.sub.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.botify.R

class ImageAdapter(private val imageList: List<Image>, private val imageListener: ImageListener) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imagePreview: ImageView = view.findViewById(R.id.image_item)
        private var currentImage: Image? = null

        init {
            imagePreview.setOnClickListener {
//                imageListener.onImageClickListener(adapterPosition)

                currentImage?.let {
                    imageListener.onImageClickListener(it)
                }
            }
        }

        fun bind(image: Image) {
            currentImage = image

            if (image.image != null) {
                imagePreview.setImageResource(image.image)
            } else {
                imagePreview.setImageResource(R.drawable.schoenbruno)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImageViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_item_view, viewGroup, false)

        return ImageViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, position: Int) {
//        val image = getItemId(position)
        imageViewHolder.bind(imageList[position])

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = imageList.size
}