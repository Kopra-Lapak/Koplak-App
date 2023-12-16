package com.example.koplakmungkin.data.repository

import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.response.ErrorResponse
import com.example.koplakmungkin.data.response.RegisterResponse
import com.example.koplakmungkin.data.retrofit.ApiService2
import com.google.gson.Gson
import retrofit2.HttpException

class KoplakRepository private constructor(
    private val apiService: ApiService2,

){
    suspend fun register(username:String,email:String, password:String):Result<RegisterResponse>{
        Result.Loading
        return try {
            val response = apiService.register(username, email, password)

            if (response.status == "BAD_REQUEST"){
                Result.Error(response.status)
            } else{
                Result.Success(response)
            }
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Result.Error(errorMessage.toString())
        }
    }
//    companion object {
//        @Volatile
//        private var instance: KoplakRepository? = null
//        fun getInstance(
//            apiService: ApiService2,
//            userPreference: UserPreference
//        ): KoplakRepository=KoplakRepository(apiService, userPreference)
//    }
}