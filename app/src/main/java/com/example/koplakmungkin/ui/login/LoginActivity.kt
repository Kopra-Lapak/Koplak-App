package com.example.koplakmungkin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koplakmungkin.databinding.ActivityLoginBinding
import com.example.koplakmungkin.ui.main.MainActivity
import com.example.koplakmungkin.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginLayout.loginBtn.setOnClickListener{
            navigateToHome()
        }
        binding.loginLayout.registerTextView.setOnClickListener {
            navigateToRegister()
        }

    }

    private fun navigateToHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToRegister(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}