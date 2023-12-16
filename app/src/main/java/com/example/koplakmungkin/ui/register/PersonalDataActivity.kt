package com.example.koplakmungkin.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koplakmungkin.BottomSheetAdapter
import com.example.koplakmungkin.R
import com.example.koplakmungkin.data.pref.UserData
import com.example.koplakmungkin.databinding.ActivityPersonalDataBinding
import com.example.koplakmungkin.ui.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDragHandleView
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataBinding
    private var selectedDate: String? = null
    private var userId: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("user_id")
        email = intent.getStringExtra("user_email")
        password = intent.getStringExtra("user_password")

        val birthEditText = binding.personalDataLayout.birthEditText
        val birthEditTextLayout = binding.personalDataLayout.birthEditTextLayout

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
                birthEditText.setText(formattedDate)
                selectedDate = formattedDate
                birthEditTextLayout.hint = null
            },
            year,
            month,
            day
        )

        birthEditText.setOnClickListener {
            datePickerDialog.show()
        }


        binding.personalDataLayout.chooseJobEditText.setOnClickListener {
            showBottomSheetJobDialog()
        }

        binding.personalDataLayout.genderEditText.setOnClickListener {
            showBottomSheetGenderDialog()
        }

        binding.personalDataLayout.cityDomicileEditText.setOnClickListener {
            showBottomSheetCityDialog()
        }
        binding.personalDataLayout.dataBtn.setOnClickListener{
            val userData = UserData(
                id = userId,
                email = email,
                password = password,
                username = binding.personalDataLayout.usernameEditText.text.toString(),
                pekerjaan = binding.personalDataLayout.chooseJobEditText.text.toString(),
                domisili = binding.personalDataLayout.cityDomicileEditText.text.toString(),
                tanggalLahir = binding.personalDataLayout.birthEditText.text.toString(),
                jenisKelamin = binding.personalDataLayout.genderEditText.text.toString(),

            )

            submitUserDataToFirebase(userData)
        }
    }

    private fun submitUserDataToFirebase(userData: UserData) {
        val database = FirebaseDatabase.getInstance().reference
        val userReference = database.child("users").child(userId.orEmpty())

        userReference.setValue(userData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registrasi berhasil.", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(this, "Registrasi gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this@PersonalDataActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showBottomSheetJobDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_choice_job, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvItem)

        val adapter = BottomSheetAdapter { selectedJob ->
            binding.personalDataLayout.chooseJobEditText.setText(selectedJob)
            bottomSheetDialog.dismiss()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val jobList = listOf("Penjual Kopra", "Pembeli Kopra")
        adapter.submitList(jobList)


        bottomSheetDialog.show()
    }

    private fun showBottomSheetCityDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_choice_city, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvItem)
        val searchView = bottomSheetView.findViewById<SearchView>(R.id.searchView)
        val dragHandle = bottomSheetView.findViewById<BottomSheetDragHandleView>(R.id.drag_handle)

        val adapter = BottomSheetAdapter { selectedCity ->
            binding.personalDataLayout.cityDomicileEditText.setText(selectedCity)
            bottomSheetDialog.dismiss()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cityList = listOf("Bontang", "Balikpapan", "Samarinda", "Sanggata", "Penajam")
        adapter.setCityList(cityList)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })

        dragHandle.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun showBottomSheetGenderDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_choice_gender, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvItem)

        val adapter = BottomSheetAdapter { selectedJob ->
            binding.personalDataLayout.genderEditText.setText(selectedJob)
            bottomSheetDialog.dismiss()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val genderList = listOf("Laki - Laki", "Perempuan")
        adapter.submitList(genderList)


        bottomSheetDialog.show()
    }
}
