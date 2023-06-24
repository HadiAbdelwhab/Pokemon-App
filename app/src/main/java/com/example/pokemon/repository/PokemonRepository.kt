package com.example.pokemon.repository


import androidx.lifecycle.LiveData
import com.example.pokemon.db.PokemonDao
import com.example.pokemon.model.Pokemon
import com.example.pokemon.network.PokemonApiService
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) {


    suspend fun getPokemons() = pokemonApiService.getPokemons()

    suspend fun insertPokemon(pokemon: Pokemon) =
        pokemonDao.insertPokemon(pokemon)

    suspend fun delete(pokemonName: String) =
        pokemonDao.deletePokemon(pokemonName)


    val allFavouritePokemons:LiveData<List<Pokemon>> = pokemonDao.getPokemons()

}