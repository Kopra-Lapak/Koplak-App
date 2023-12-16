package com.example.koplakmungkin.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.dicoding.storyapp.view.ViewModelFactory
import com.example.koplakmungkin.data.Result
import com.example.koplakmungkin.data.repository.KoplakRepository
import com.example.koplakmungkin.data.response.RegisterResponse
import com.example.koplakmungkin.databinding.ActivityRegisterBinding
import com.example.koplakmungkin.ui.login.LoginActivity
import com.example.koplakmungkin.ui.opening.OpeningActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerLayout.backBtn.setOnClickListener {
            backBtn()
        }
        binding.registerLayout.loginTextView.setOnClickListener {
            navigateToLogin()
        }
        setupAction()
    }
    private fun setupAction(){
        binding.registerLayout.nextBtn.setOnClickListener {
            val email = binding.registerLayout.emailEditText.toString()
            val password = binding.registerLayout.passwordEditText.toString()
            val username = binding.registerLayout.usernameEditText.toString()

            viewModel.register(username, email, password)
            viewModel.registrationResult.observe(this){
                when(it){
                    is Result.Loading ->{
                        showLoading(true)
                    }
                    is Result.Success ->{
                        showLoading(false)
                        val response: RegisterResponse = it.data
                        AlertDialog.Builder(this).apply {
                            setTitle("Mantep!")
                            setMessage(response.status)
                            setPositiveButton("Lanjut"){ _,_ ->
                            }
                            create()
                            show()
                        }
                        val intent = Intent (this, PersonalDataActivity::class.java)
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        val errorMessage: String = it.error
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops")
                            setMessage(errorMessage)
                            setPositiveButton("OKE") { _, _ ->
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun backBtn(){
        val intent = Intent (this, OpeningActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                registerLayout.progressBar.visibility = View.VISIBLE
            } else {
                registerLayout.progressBar.visibility = View.GONE
            }
        }
    }
}