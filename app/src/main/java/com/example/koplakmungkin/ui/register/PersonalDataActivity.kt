package com.example.koplakmungkin.ui.register

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koplakmungkin.BottomSheetAdapter
import com.example.koplakmungkin.R
import com.example.koplakmungkin.databinding.ActivityPersonalDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDragHandleView
import java.util.Calendar

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataBinding
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.personalDataLayout.dataBtn.setOnClickListener {

        }
    }

    private fun showBottomSheetJobDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_choice_job, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvItem)

        val adapter = BottomSheetAdapter { selectedJob ->
            binding.personalDataLayout.chooseJobEditText.setText(selectedJob)
            bottomSheetDialog.dismiss()
            val endIconDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_keyboard_arrow_down_24)
            binding.personalDataLayout.chooseJobEditTextLayout.endIconDrawable = endIconDrawable
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val jobList = listOf("Penjual Kopra", "Pembeli Kopra")
        adapter.submitList(jobList)

        val endIconDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_keyboard_arrow_up_24)
        binding.personalDataLayout.chooseJobEditTextLayout.endIconDrawable = endIconDrawable

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
            val endIconDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_keyboard_arrow_down_24)
            binding.personalDataLayout.cityDomicileLayout.endIconDrawable = endIconDrawable
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cityList = listOf("aa", "bb", "cc", "dd", "bc")
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
            val endIconDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_keyboard_arrow_down_24)
            binding.personalDataLayout.chooseJobEditTextLayout.endIconDrawable = endIconDrawable
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val genderList = listOf("Laki - Laki", "Perempuan")
        adapter.submitList(genderList)

        val endIconDrawable = ContextCompat.getDrawable(this, R.drawable.baseline_keyboard_arrow_up_24)
        binding.personalDataLayout.chooseJobEditTextLayout.endIconDrawable = endIconDrawable

        bottomSheetDialog.show()
    }
}
