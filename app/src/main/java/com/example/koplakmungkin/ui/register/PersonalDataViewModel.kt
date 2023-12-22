package com.example.koplakmungkin.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.ProfileResponse
import com.example.koplakmungkin.data.response.RegisterResponse
import kotlinx.coroutines.launch

class PersonalDataViewModel(private val koplakRepository: KoplakRepository): ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> get() = _profileResult

    fun regisProfile(imageProfile: String, fullname: String, address: String, birth: String, gender: String){
        _profileResult.value = Result.Loading

        viewModelScope.launch {
            val result = koplakRepository.regisProfile( imageProfile,fullname, address, birth, gender)
            _profileResult.value = result

        }
    }
}