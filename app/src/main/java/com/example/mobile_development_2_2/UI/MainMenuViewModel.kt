package com.example.mobile_development_2_2.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobile_development_2_2.CharacterApplication
import com.example.mobile_development_2_2.ICharacterRepository
import com.example.mobile_development_2_2.RickAndMortyCharactersData
import kotlinx.coroutines.launch

class MainMenuViewModel(private val characterRepository : ICharacterRepository) : ViewModel() {
    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CharacterApplication)
                val characterRepository = application.container.characterRepository
                MainMenuViewModel(characterRepository = characterRepository)
            }
        }
    }
    private val _items = MutableLiveData<RickAndMortyCharactersData>()
    val items: LiveData<RickAndMortyCharactersData> get() = _items
    private var _errorText = MutableLiveData<String>()
    val errorText: LiveData<String> get() = _errorText
    private var _currentPage = 1

    fun nextPage() {
        _currentPage += 1
    }

    fun pickPage(newPage: Int){
        _currentPage = newPage
    }


    fun fetchCharactersRequest() {
        viewModelScope.launch {
            try {
                val response = characterRepository.getCharacters(_currentPage)
                        .getOrThrow()
                _items.value = response
            } catch (error: Exception){
                _errorText.postValue(error.message)
            }
        }
    }
}