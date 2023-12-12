package com.example.koplakmungkin.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koplakmungkin.databinding.ActivityRegisterBinding
import com.example.koplakmungkin.ui.opening.OpeningActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerLayout.nextBtn.setOnClickListener{
            personalDataIntent()
        }
        binding.registerLayout.backBtn.setOnClickListener {
            backBtn()
        }
    }

    private fun personalDataIntent() {
        val intent = Intent(this, PersonalDataActivity::class.java)
        startActivity(intent)
    }
    private fun backBtn(){
        val intent = Intent (this, OpeningActivity::class.java)
        startActivity(intent)
        finish()
    }
}