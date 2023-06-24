package com.example.pokemon.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokemon.model.Pokemon

@Dao
interface PokemonDao {

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("delete from fav_table where name=:pokemonName")
    suspend fun deletePokemon(pokemonName: String)


    @Query("select * from fav_table")
    fun getPokemons(): LiveData<List<Pokemon>>


}