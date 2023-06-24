package com.example.pokemon.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.adapters.PokemonAdapter
import com.example.pokemon.databinding.FragmentFavouriteBinding
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.MainActivity
import com.example.pokemon.viewmodel.PokemonViewModel
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private lateinit var pokemonViewModel: PokemonViewModel
    private var _binding: FragmentFavouriteBinding? = null
    private lateinit var pokemonAdapter: PokemonAdapter
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonViewModel = (activity as MainActivity).pokemonViewModel

        setUpRecyclerView()

        setupSwipe()

        pokemonViewModel.allFavouritePokemons.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                pokemonAdapter.updateData(it)
            }

        })

    }

    private fun setupSwipe() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPokemonPosition = viewHolder.adapterPosition
                val swipedPokemon = pokemonAdapter.getPokemonAt(swipedPokemonPosition)
                pokemonViewModel.deletePokemon(swipedPokemon.name)
                pokemonAdapter.notifyDataSetChanged()
                view?.let {
                    Snackbar.make(it, "Successfully deleted pokemon", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            pokemonViewModel.insertPokemon(pokemon = swipedPokemon)
                        }
                        show()
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.favouriteFragmentRecyclerView)
    }


    private fun setUpRecyclerView() {
        pokemonAdapter = PokemonAdapter(emptyList())
        binding.favouriteFragmentRecyclerView.apply {
            adapter = pokemonAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}