package com.example.mobile_development_2_2

interface ICharacterRepository {
    suspend fun getCharacters(page: Int): Result<RickAndMortyCharactersData>
}

class NetworkCharacterRepository(private val apiService: GetCharacters) : ICharacterRepository {
    override suspend fun getCharacters(page: Int): Result<RickAndMortyCharactersData> {
        return try {
            val response = apiService.getAllCharacters(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Пустой ответ от сервера"))
            } else {
                Result.failure(Exception("Ошибка сервера: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}