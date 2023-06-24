package com.example.pokemon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.model.Pokemon
import com.example.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _getPokemonsList: MutableLiveData<List<Pokemon?>> = MutableLiveData()
    val getPokemonsList: LiveData<List<Pokemon?>> get() = _getPokemonsList


    val allFavouritePokemons: LiveData<List<Pokemon>>

    init {
        getPokemons()
        allFavouritePokemons = repository.allFavouritePokemons
    }

    private fun getPokemons() {
        viewModelScope.launch {
            try {
                val response = repository.getPokemons()
                val list = response.body()?.pokemons
                if (list != null) {
                    for (pokemon in list) {
                        val url = pokemon.url
                        val pokemonIndex = url.split("/").last { it.isNotEmpty() }
                        pokemon.url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonIndex}.png"
                    }
                }
                _getPokemonsList.value = list
            } catch (e: Exception) {

            }
        }
    }

    fun insertPokemon(pokemon: Pokemon) = viewModelScope.launch {
        repository.insertPokemon(pokemon)
    }

    fun deletePokemon(pokemonName: String) = viewModelScope.launch {
        repository.delete(pokemonName)
    }




}



