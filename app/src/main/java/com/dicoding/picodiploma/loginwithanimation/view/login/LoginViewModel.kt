package com.dicoding.picodiploma.loginwithanimation.view.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String,callback: (Boolean) -> Unit){
        viewModelScope.launch {
            try {
                // Panggil fungsi login dari repository
                repository.login(email, password)
                callback(true)


            } catch (e: Exception) {
                // Login gagal, catat pesan kesalahan ke dalam log
                Log.e("LoginViewModel", "Login gagal: ${e.message}")
                e.printStackTrace()
                callback(false)
            }
        }
    }
}