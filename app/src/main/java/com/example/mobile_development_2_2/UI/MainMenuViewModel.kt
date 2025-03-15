package com.example.mobile_development_2_2.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_development_2_2.RickAndMortyApi
import com.example.mobile_development_2_2.RickAndMortyCharactersData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainMenuViewModel : ViewModel() {
    private val _items = MutableLiveData<RickAndMortyCharactersData>()
    val items: LiveData<RickAndMortyCharactersData> get() = _items
    private var _errorText = ""
    private var _currentPage = 1

    fun nextPage() {
        _currentPage = _currentPage + 1
    }

    fun fetchCharactersRequest() {
        RickAndMortyApi.instance.getAllCharacters(_currentPage)
            .enqueue(object : Callback<RickAndMortyCharactersData> {
                override fun onResponse(
                    call: Call<RickAndMortyCharactersData>,
                    response: Response<RickAndMortyCharactersData>
                ) {
                    if (response.isSuccessful) {
                        _items.value = response.body()
                    }
                }

                override fun onFailure(call: Call<RickAndMortyCharactersData>, t: Throwable) {
                    if (t is IOException) {
                        _errorText = "Проблема с подключением к сети!"
                    } else {
                        _errorText = "Ошибка!"
                    }
                }
            })
    }
}