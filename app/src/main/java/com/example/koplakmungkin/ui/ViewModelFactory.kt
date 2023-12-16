package com.dicoding.storyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.ui.register.RegisterViewModel


class ViewModelFactory(private val repository: KoplakRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {


            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                RegisterViewModel(repository) as T
            }
             else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}