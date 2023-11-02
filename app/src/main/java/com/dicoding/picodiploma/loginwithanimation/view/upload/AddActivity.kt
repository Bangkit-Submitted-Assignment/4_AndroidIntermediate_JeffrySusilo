package com.dicoding.picodiploma.loginwithanimation.view.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.AddNewStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.uriToFile
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityAddBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        //binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private fun startCameraX() {
        Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            val description = binding.deskripsiEditText.text.toString()

            showLoading(true)

            lifecycleScope.launch {
                try {
                    val response = viewModel.uploadStory(imageFile, description)
                    showToast(response.message)
                    showLoading(false)
                    navigateToListActivity()
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, AddNewStoryResponse::class.java)
                    showToast(errorResponse.message)
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToListActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}