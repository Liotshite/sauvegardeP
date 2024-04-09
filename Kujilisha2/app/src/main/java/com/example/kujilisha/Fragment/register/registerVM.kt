package com.example.kujilisha.Fragment.register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kujilisha.data.AuthRepository

class AuthSignUpViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun signUp(email: String, password: String, callback: (Boolean) -> Unit) {
        authRepository.signUp(email, password) { success ->
            callback(success)
        }
    }
}