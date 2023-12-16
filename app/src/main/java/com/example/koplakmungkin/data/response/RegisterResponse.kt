package com.example.koplakmungkin.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: Message,

    @field:SerializedName("data")
    val data: String?

)

data class Message(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("role")
    val role: String? = null
)