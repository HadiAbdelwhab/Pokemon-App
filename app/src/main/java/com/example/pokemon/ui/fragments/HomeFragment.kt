package com.example.pokemon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.adapters.PokemonAdapter
import com.example.pokemon.databinding.FragmentHomeBinding
import com.example.pokemon.model.Pokemon
import com.example.pokemon.ui.MainActivity
import com.example.pokemon.viewmodel.PokemonViewModel

class HomeFragment:Fragment(R.layout.fragment_home) {

    private var _binding:FragmentHomeBinding?=null
    private lateinit var pokemonViewModel: PokemonViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonViewModel = (activity as MainActivity).pokemonViewModel

        setUpRecyclerView()

        setupSwipe()



        pokemonViewModel.getPokemonsList.observe(viewLifecycleOwner, Observer {
            pokemonAdapter.updateData(it as List<Pokemon>)

        })

        binding.goToFavouriteButton.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_favouriteFragment)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        pokemonAdapter= PokemonAdapter(emptyList())
        binding.homeFragmentRecyclerView.apply {
            adapter = pokemonAdapter
            layoutManager = LinearLayoutManager(activity)
        }
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
                if (swipedPokemon != null) {
                    pokemonViewModel.insertPokemon(swipedPokemon)
                    Toast.makeText(context, "added to favourite ", Toast.LENGTH_SHORT).show()
                }
                pokemonAdapter.notifyDataSetChanged()

            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.homeFragmentRecyclerView)
    }



}