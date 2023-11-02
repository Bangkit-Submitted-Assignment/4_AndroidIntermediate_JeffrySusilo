package com.dicoding.picodiploma.loginwithanimation.view.upload

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.AddNewStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository

class UploadViewModel(private val repository: UserRepository) : ViewModel() {

    suspend fun uploadStory(imageFile: java.io.File, description: String): AddNewStoryResponse {
        return repository.uploadStory(imageFile, description)
    }
}