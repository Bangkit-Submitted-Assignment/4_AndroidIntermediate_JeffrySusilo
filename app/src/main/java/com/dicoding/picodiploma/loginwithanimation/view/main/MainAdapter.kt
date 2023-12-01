package com.dicoding.picodiploma.loginwithanimation.view.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.ListStoryItem
import com.squareup.picasso.Picasso

class MainAdapter(private val onItemClick: (String) -> Unit) :
    PagingDataAdapter<ListStoryItem, MainAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_item_name)
        val descriptionTextView: TextView = view.findViewById(R.id.tv_item_description)
        val photoImageView: ImageView = view.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.nameTextView.text = story?.name
        holder.descriptionTextView.text = story?.description

        Log.d("Image URL", story?.photoUrl ?: "URL gambar kosong")

        Picasso.get()
            .load(story?.photoUrl)
            .into(holder.photoImageView)

        holder.itemView.setOnClickListener {
            story?.id?.let { storyId ->
                onItemClick(storyId)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

