package com.example.mobile_development_2_2.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_development_2_2.RickAndMortyCharacterAdapter
import com.example.mobile_development_2_2.databinding.FragmentMainMenuBinding

class MainMenu : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null

    private var adapter: RickAndMortyCharacterAdapter = RickAndMortyCharacterAdapter(ArrayList())
    private val binding get() = _binding!!
    private val viewModel: MainMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchCharactersRequest()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupView()
        setupObservers()
        return root
    }

    private fun setupView(){
        binding.apply {
            RecyclerView.layoutManager = LinearLayoutManager(context)
            RecyclerView.adapter = adapter
            RecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        }
    }

    private fun setupObservers(){
        viewModel.items.observe(viewLifecycleOwner) { result ->
            adapter.addCharacters(result.results)
        }
    }
}