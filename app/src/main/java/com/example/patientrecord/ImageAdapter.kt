package com.example.patientrecord

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val context: Context) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private var imageData: Bitmap? = null

    fun updateImageData(imageData: Bitmap) {
        this.imageData = imageData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // Set the Bitmap to ImageView directly
        holder.imageView.setImageBitmap(imageData)

        holder.itemView.setOnClickListener {
            // Handle click event to show full image view
            // You can implement your logic to show full image view here
            // For example, you can open a new activity or dialog to display the image in full size
        }
    }

    override fun getItemCount(): Int {
        return if (imageData != null) {
            1
        } else {
            0
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}