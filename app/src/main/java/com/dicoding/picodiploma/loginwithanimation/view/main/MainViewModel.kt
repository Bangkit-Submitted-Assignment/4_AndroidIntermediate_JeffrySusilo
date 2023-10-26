package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.DetailStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val storyResponse = MutableLiveData<List<ListStoryItem?>>()

    fun getStoryResponse(): LiveData<List<ListStoryItem?>> {
        return storyResponse
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStory() {
        viewModelScope.launch {
            try {
                // Panggil fungsi getStories dari repository
                val response = repository.getStory()
                val stories = repository.getStory()?.listStory ?: emptyList()
                storyResponse.postValue(stories)

            } catch (e: Exception) {
                // Tangani jika terjadi kesalahan saat memuat cerita
                // Anda dapat menampilkan pesan kesalahan atau melakukan tindakan lain yang sesuai
            }
        }
    }

    fun getDetailStory(storyId: String): LiveData<DetailStoryResponse> {
        val detailStoryLiveData = MutableLiveData<DetailStoryResponse>()
        viewModelScope.launch {
            try {
                // Panggil fungsi getDetailStory dari repository dengan storyId yang diberikan
                val response = repository.getDetailStory(storyId)
                detailStoryLiveData.postValue(response)
            } catch (e: Exception) {
                // Tangani jika terjadi kesalahan saat memuat detail cerita
                // Anda dapat menampilkan pesan kesalahan atau melakukan tindakan lain yang sesuai
            }
        }
        return detailStoryLiveData
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}