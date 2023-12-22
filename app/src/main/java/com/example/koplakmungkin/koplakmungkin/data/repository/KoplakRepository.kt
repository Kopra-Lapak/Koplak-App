package com.example.koplakmungkin.data.repository

import android.util.Log
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.model.UserData
import com.example.koplakmungkin.data.pref.UserPref
import com.example.koplakmungkin.data.response.ErrorResponse
import com.example.koplakmungkin.data.response.LoginResponse
import com.example.koplakmungkin.data.response.ProfileResponse
import com.example.koplakmungkin.data.response.RegisterResponse
import com.example.koplakmungkin.data.retrofit.ApiService
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class KoplakRepository private constructor(
    private val apiService: ApiService,
    private val userPref: UserPref
) {

    suspend fun saveSession(user: UserData) {
        userPref.saveSession(user)
    }

    fun getSession(): Flow<UserData> {
        return userPref.getSession()
    }

    suspend fun logout() {
        userPref.logout()
    }

    suspend fun register(username: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = apiService.register(username, email, password)

            if (response.status == "BAD_REQUEST") {
                Result.Error(response.status)
            } else {
                Result.Success(response)
            }
        } catch (e: HttpException) {
            return if (e.response()?.errorBody() != null) {
                try {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    Result.Error(errorBody.message ?: "Unknown error")
                } catch (jsonException: JsonSyntaxException) {
                    Result.Error("JSON parsing error")
                }
            } else {
                Result.Error("Unknown error")
            }
        }
    }

    suspend fun regisProfile(imageProfile: String, fullname: String, address: String, birth: String, gender: String): Result<ProfileResponse> {

        return try {
            val response = apiService.profile(getToken(), imageProfile, fullname, address, birth, gender)

            if (response.status == "BAD_REQUEST") {
                Log.d("tag", "regisprofile bad")
                Result.Error(response.status)
            } else {
                Log.d("tag", "regisprofile success")
                Result.Success(response)
            }
        } catch (e: HttpException) {
            return if (e.response()?.errorBody() != null) {
                try {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    Result.Error(errorBody.message ?: "Unknown error")
                } catch (jsonException: JsonSyntaxException) {
                    Result.Error("JSON parsing error")
                }
            } else {
                Result.Error("Unknown error")
            }
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)

            if (response.status == "BAD_REQUEST") {
                Result.Error(response.status)
            } else {
                val userData = UserData(email= email, token = response.data.accessToken, isLogin = true)
                saveSession(userData)
                Result.Success(response)
            }
        } catch (e: HttpException) {
            return if (e.response()?.errorBody() != null) {
                try {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    Result.Error(errorBody.message ?: "Unknown error")
                } catch (jsonException: JsonSyntaxException) {
                    Result.Error("JSON parsing error")
                }
            } else {
                Result.Error("Unknown error")
            }
        }
    }

    private suspend fun getToken(): String {
        val token = userPref.getToken()
        Log.d("ApiConfig2", "Retrieved Token: $token")
        return token
    }

    companion object {
        private var instance: KoplakRepository? = null

        fun getInstance(apiService: ApiService, userPref : UserPref): KoplakRepository {
            return instance ?: synchronized(this) {
                instance ?: KoplakRepository(apiService, userPref).also { instance = it }
            }
        }
    }
}