package com.example.mobile_development_2_2

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GetCharacters {
    @GET("character")
    fun getAllCharacters(@Query("page") page : Int) : Call<RickAndMortyCharactersData>
}

object RickAndMortyApi {
    private const val baseUrl = "https://rickandmortyapi.com/api/"
    val instance: GetCharacters by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GetCharacters::class.java)
    }
}