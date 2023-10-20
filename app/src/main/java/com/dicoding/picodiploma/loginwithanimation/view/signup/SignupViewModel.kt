package com.dicoding.picodiploma.loginwithanimation.view.signup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            // Panggil fungsi register dari repository
            val response = userRepository.register(name, email, password)

            // Lakukan sesuatu dengan respons, misalnya menampilkan pesan atau mengarahkan ke halaman lain
        }
    }
}
