package com.example.mobile_development_2_2

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GetCharacters {
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page : Int) : Response<RickAndMortyCharactersData>
}
class RickAndMortyApi{
    companion object {
        private const val baseUrl = "https://rickandmortyapi.com/api/"
        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }
}


