package com.dicoding.picodiploma.loginwithanimation.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.picodiploma.loginwithanimation.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        // Panggil endpoint register dari ApiService
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String) {
        val response = apiService.login(email, password)
        if (response.loginResult != null) {
            // Login berhasil, simpan token ke dalam DataStore
            userPreference.saveToken(response.loginResult.token)
        } else {
            // Handle kesalahan jika login gagal
            throw Exception("Login gagal")
        }
    }

    suspend fun getStory():StoryResponse{
        // Panggil fungsi getStories dari apiService
        val token = userPreference.getToken() ?: ""

        // Panggil fungsi getStories dari apiService dengan token
        return apiService.getStories("Bearer $token")
    }

    suspend fun getDetailStory(storyId: String): DetailStoryResponse {
        val token = userPreference.getToken() ?: ""
        // Panggil fungsi getDetailStory dari apiService dengan token
        return apiService.getDetailStory("Bearer $token", storyId)
    }

    suspend fun getStoriesWithLocation(): StoryResponse{
        val token = userPreference.getToken() ?: ""
        return apiService.getStoriesWithLocation("Bearer $token")
    }

    suspend fun getStoryPager(): Flow<PagingData<ListStoryItem>> {
        val token = userPreference.getToken() ?: ""
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoryPagingSource(apiService,"Bearer $token") }
        ).flow
    }

    suspend fun uploadStory(
        imageFile: File,
        description: String
    ): AddNewStoryResponse {
        val token = userPreference.getToken() ?: ""

        val requestImageFile = imageFile.reduceFileImage().asRequestBody("image/jpeg".toMediaType())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())

        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        return apiService.uploadStory("Bearer $token", multipartBody, descriptionRequestBody)
    }



    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference,apiService)
            }.also { instance = it }
    }
}