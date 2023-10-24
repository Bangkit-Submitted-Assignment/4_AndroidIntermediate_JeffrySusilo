package com.dicoding.picodiploma.loginwithanimation.view.signup
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val userRepository: UserRepository) : ViewModel() {

//    val user: LiveData<UserModel> = liveData {
//        userRepository.getUser().collect { userModel ->
//            emit(userModel)
//        }
//    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {

            try {
                // Panggil fungsi register dari repository
                val response = userRepository.register(name, email, password)

                // Registrasi berhasil, tampilkan pesan sukses dan catat pesan log
                Log.i("SignupViewModel", "Registrasi berhasil: $response")

            } catch (e: Exception) {
                // Registrasi gagal, catat pesan kesalahan ke dalam log
                Log.e("SignupViewModel", "Registrasi gagal: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}
