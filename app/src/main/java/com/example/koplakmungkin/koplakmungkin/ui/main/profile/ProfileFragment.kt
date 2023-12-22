package com.example.koplakmungkin.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koplakmungkin.databinding.FragmentProfileBinding
import com.example.koplakmungkin.ui.register.PersonalDataActivity


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setProfile()

        return root
    }

    private fun setProfile(){
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(binding.root.context, PersonalDataActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {

    }
}