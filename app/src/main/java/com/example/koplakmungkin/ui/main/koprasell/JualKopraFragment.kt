package com.example.koplakmungkin.ui.main.koprasell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.koplakmungkin.R
import com.example.koplakmungkin.databinding.FragmentJualKopraBinding


class JualKopraFragment : Fragment() {
    private var _binding: FragmentJualKopraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJualKopraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}