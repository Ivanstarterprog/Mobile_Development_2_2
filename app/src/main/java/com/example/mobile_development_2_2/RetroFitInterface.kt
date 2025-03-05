package com.example.mobile_development_2_2

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface GetCharacters {
    @GET("character")
    suspend fun getAllCharacters() : Response<RickAndMortyCharactersData>
}

object RickAndMortyApi {
    val baseUrl = "https://rickandmortyapi.com/api/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}