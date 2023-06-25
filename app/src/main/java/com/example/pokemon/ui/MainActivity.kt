package com.example.pokemon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.adapters.PokemonAdapter
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.model.Pokemon
import com.example.pokemon.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {


    private lateinit var navController: NavController
    lateinit var pokemonViewModel: PokemonViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        pokemonViewModel= ViewModelProvider(this)[PokemonViewModel::class.java]


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this,navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}


