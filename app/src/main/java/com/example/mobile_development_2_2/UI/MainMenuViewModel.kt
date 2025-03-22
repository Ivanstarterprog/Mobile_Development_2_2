package com.example.mobile_development_2_2.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_development_2_2.RickAndMortyApi
import com.example.mobile_development_2_2.RickAndMortyCharactersData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainMenuViewModel : ViewModel() {
    private val _items = MutableLiveData<RickAndMortyCharactersData>()
    val items: LiveData<RickAndMortyCharactersData> get() = _items
    private var _errorText = MutableLiveData<String>()
    val errorText: LiveData<String> get() = _errorText
    private var _currentPage = 1

    fun nextPage() {
        _currentPage += 1
    }

    fun fetchCharactersRequest() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RickAndMortyApi.instance.getAllCharacters(_currentPage)
                }
                _items.value = response
            } catch (t: Throwable) {
                _errorText.value = when (t) {
                    is IOException -> "Проблема с подключением к сети!"
                    else -> "Ошибка!"
                }
            }
        }
    }
}