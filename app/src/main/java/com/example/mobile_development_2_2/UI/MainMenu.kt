package com.example.mobile_development_2_2.UI

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = RickAndMortyCharacterAdapter(ArrayList())
        binding.RecyclerView.layoutManager = LinearLayoutManager(context)

        binding.RecyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner){ result ->
            adapter.addCharacters(result.results)
        }

        binding.RecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Проверяем, достигли ли конца списка
                if (!adapter.isLoading()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 20 // Минимальное количество элементов для загрузки
                    ) {

                        adapter.setLoading(true)
                        viewModel.fetchCharactersRequest()
                        viewModel.nextPage()
                        adapter.setLoading(false)
                    }
                }
            }
        })


        viewModel.fetchCharactersRequest()
        return root
    }
}