package com.example.koplakmungkin.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.koplakmungkin.R

class SharedPreferenceManager (context: Context) {

    private val preference = context.getSharedPreferences(
        context.getString(R.string.app_name),AppCompatActivity.MODE_PRIVATE
    )
    private val editor = preference.edit()

    private val keyIsFirstTime = "isFirstTime"

    var isFirstTime
    get() = preference.getBoolean(keyIsFirstTime,true)
    set(value) {
        editor.putBoolean(keyIsFirstTime,value)
        editor.commit()
    }
}