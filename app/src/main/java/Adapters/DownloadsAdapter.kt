package com.example.infinitywallpapers.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infinitywallpapers.R

class DownloadsAdapter(private val imagePaths: List<String>) : RecyclerView.Adapter<DownloadsAdapter.ImageViewHolder>() {

    // ViewHolder class to hold each item view
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.item_downloads_img)
    }

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_downloads, parent, false)
        return ImageViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = imagePaths[position]
        Glide.with(holder.itemView.context)
            .load(imagePath)
            .into(holder.imageView)
    }

    // Return the size of the dataset
    override fun getItemCount() = imagePaths.size
}
