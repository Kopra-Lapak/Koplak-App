package com.example.koplakmungkin.data.model

data class UserData(
    val id: String? = null,
    val username: String? = null,
    val token: String,
    val isLogin: Boolean = false
)