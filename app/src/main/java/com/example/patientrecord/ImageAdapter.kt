package com.example.patientrecord

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patientrecord.model.RecordImage

class ImageAdapter: ListAdapter<RecordImage, ImageAdapter.ImageViewHolder>(ImageDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(recordImage: RecordImage) {
            val imageData = recordImage.data
            val decodedBytes = Base64.decode(imageData, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            Glide.with(itemView.context)
                .load(bitmap)
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.error_image) // Placeholder image if loading fails
                .into(imageView)
        }
    }

    private class ImageDiffCallback : DiffUtil.ItemCallback<RecordImage>() {
        override fun areItemsTheSame(oldItem: RecordImage, newItem: RecordImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RecordImage, newItem: RecordImage): Boolean {
            return oldItem == newItem
        }
    }
}