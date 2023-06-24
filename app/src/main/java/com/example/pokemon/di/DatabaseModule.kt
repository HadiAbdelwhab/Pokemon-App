package com.example.pokemon.di

import android.content.Context
import androidx.room.Room
import com.example.pokemon.db.PokemonDao
import com.example.pokemon.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase): PokemonDao {
        return pokemonDatabase.getPokemonDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            appContext,
            PokemonDatabase::class.java,
            "pokemon_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}