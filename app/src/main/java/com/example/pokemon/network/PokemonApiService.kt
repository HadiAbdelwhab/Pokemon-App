package com.example.pokemon.network

import com.example.pokemon.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemons(): Response<PokemonResponse>
}