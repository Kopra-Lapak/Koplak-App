package com.example.koplakmungkin.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.RegisterResponse
import kotlinx.coroutines.launch


class RegisterViewModel(private val koplakRepository: KoplakRepository) : ViewModel() {
    private val _registrationResult = MutableLiveData<Result<RegisterResponse>>()
    val registrationResult: LiveData<Result<RegisterResponse>> get() = _registrationResult

    fun register(username: String, email: String, password: String) {
        _registrationResult.value = Result.Loading

        viewModelScope.launch {
            val result = koplakRepository.register(username, email, password)
            _registrationResult.value = result
            Log.d("tag", "simpan data")
        }
    }
}