package com.example.mobile_development_2_2.UI

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_development_2_2.R
import com.example.mobile_development_2_2.RickAndMortyCharacterAdapter
import com.example.mobile_development_2_2.databinding.FragmentMainMenuBinding

class MainMenu : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null
    companion object {
        fun newInstance() = MainMenu()
    }
    private lateinit var adapter: RickAndMortyCharacterAdapter
    private val binding get() = _binding!!
    private val viewModel: MainMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.layoutManager = LinearLayoutManager(context)
        return root
    }
}