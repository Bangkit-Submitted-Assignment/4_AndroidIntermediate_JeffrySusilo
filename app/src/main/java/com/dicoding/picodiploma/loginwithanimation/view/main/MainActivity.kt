package com.dicoding.picodiploma.loginwithanimation.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.customView.PasswordErrorCustomView
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.maps.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.upload.AddActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var passwordErrorCustomView: PasswordErrorCustomView
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) {user ->
            val token = user.token
            Log.d("INI TOKEN", token)
            val adapter = mainAdapter
            binding.storyRecycleView.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            viewModel.getStoryPager(token).observe(this) {stories ->
                if (stories != null) {
                    adapter.submitData(lifecycle, stories)
                }
            }
        }

        setupView()
        setupToolbar()
        setupRecyclerView()
        setupAction()
        setupFloatingActionButton()
        //viewModel.getStory()
        playAnimation()

    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter { storyId ->
            // Panggil intent untuk membuka DetailStoryActivity dengan storyId yang dipilih
            val intent = Intent(this, DetailStoryActivity::class.java)
            intent.putExtra("STORY_ID", storyId)
            startActivity(intent)
        }
        binding.storyRecycleView.adapter = mainAdapter
        binding.storyRecycleView.layoutManager = LinearLayoutManager(this)
    }


    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set click listener for the map button
        val mapButton: ImageButton = findViewById(R.id.mapButton)
        mapButton.setOnClickListener {
            handleMapButtonClick()
        }
    }

    private fun handleMapButtonClick() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        supportActionBar?.title="List Story"
    }

    private fun setupAction() {

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(name,message,logout)
            startDelay = 100
        }.start()
    }

    private fun setupFloatingActionButton() {
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            navigateToAddActivity()
        }
    }

    private fun navigateToAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }
}