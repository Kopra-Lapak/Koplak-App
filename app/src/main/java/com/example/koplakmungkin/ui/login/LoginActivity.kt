package com.example.koplakmungkin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.koplakmungkin.data.pref.UserData
import com.example.koplakmungkin.databinding.ActivityLoginBinding
import com.example.koplakmungkin.ui.main.MainActivity
import com.example.koplakmungkin.ui.register.RegisterActivity
import com.example.koplakmungkin.utils.SharedPreferenceManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferenceManager: SharedPreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferenceManager = SharedPreferenceManager(this)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

//        binding.loginLayout.loginBtn.setOnClickListener{
//            navigateToHome()
//        }
        binding.loginLayout.registerTextView.setOnClickListener {
            navigateToRegister()
        }
        loginButtonHandler()

    }

    private fun navigateToHome(userId: String?) {
        sharedPreferenceManager.userId = userId
        sharedPreferenceManager.isLoggedIn = true
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToRegister(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun loginUser(email: String, password: String){
        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val userData = userSnapshot.getValue(UserData::class.java)

                            if (userData != null && userData.password == password) {
                                val userId = userSnapshot.key
                                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                                navigateToHome(userId)
                                return
                            }
                        }
                    }
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@LoginActivity, "Database Error : ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun loginButtonHandler() {
        binding.loginLayout.loginBtn.setOnClickListener {
            val email = binding.loginLayout.emailEditText.text.toString()
            val password = binding.loginLayout.passwordEditText.text.toString()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                loginUser(email, password)
            } else {
                Toast.makeText(this@LoginActivity, "All fields are mandatory ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}