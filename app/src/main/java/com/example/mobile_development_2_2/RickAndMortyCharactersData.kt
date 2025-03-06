package com.example.mobile_development_2_2

import com.google.gson.annotations.SerializedName


data class Info(
    @SerializedName("count") val countOfChars: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val nextPage: String?,
    @SerializedName("prev") val previousPage: String?
)

data class ResultOfCharactersQueue(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String
)

data class RickAndMortyCharactersData(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: ArrayList<ResultOfCharactersQueue>
)
