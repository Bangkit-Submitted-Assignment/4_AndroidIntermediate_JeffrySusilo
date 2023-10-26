package com.dicoding.picodiploma.loginwithanimation.view.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.squareup.picasso.Picasso

class DetailStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)

        // Ambil data id cerita dari Intent
        val storyId = intent.getStringExtra("STORY_ID")

        // Panggil metode untuk mendapatkan detail cerita berdasarkan storyId
        fetchDetailStory(storyId)
    }

    private fun fetchDetailStory(storyId: String?) {
        // Anda dapat menggunakan repository dan viewModel Anda untuk mendapatkan detail cerita
        val viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))
            .get(MainViewModel::class.java)

        if (storyId != null) {
            viewModel.getDetailStory(storyId).observe(this, { detailStory ->
                if (detailStory != null) {
                    // Isi tampilan dengan data detail cerita
                    val imageView = findViewById<ImageView>(R.id.foto_detail)
                    val nameTextView = findViewById<TextView>(R.id.nama_detail)
                    val descriptionTextView = findViewById<TextView>(R.id.deskripsi_detail)

                    // Set gambar
                    Picasso.get().load(detailStory.story?.photoUrl).into(imageView)

                    // Set nama dan deskripsi
                    nameTextView.text = detailStory.story?.name
                    descriptionTextView.text = detailStory.story?.description
                }
            })
        }
    }
}