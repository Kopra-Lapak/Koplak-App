package com.example.koplakmungkin.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koplakmungkin.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerLayout.nextBtn.setOnClickListener{
            personalDataIntent()
        }
    }

    private fun personalDataIntent() {
        val intent = Intent(this, PersonalDataActivity::class.java)
        startActivity(intent)
    }
}