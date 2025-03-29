package com.example.mobile_development_2_2

import android.app.Application
import retrofit2.Retrofit

interface AppContainer{
    val characterRepository : ICharacterRepository
}

class DefaultContainer : AppContainer{
    private val retrofitHelper : Retrofit = RickAndMortyApi.getInstance()
    private val getter = retrofitHelper.create(GetCharacters::class.java)

    override val characterRepository: ICharacterRepository by lazy{
        NetworkCharacterRepository(getter)
    }
}

class CharacterApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}