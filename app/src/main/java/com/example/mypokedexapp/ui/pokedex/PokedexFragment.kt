package com.example.mypokedexapp.ui.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R

class PokedexFragment : Fragment() {
    private val pokedexViewModel: PokedexViewModel by viewModels {
        PokedexViewModelFactory((activity?.application as PokemonApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pokedex, container, false)

        val recyclerview = root.findViewById<RecyclerView>(R.id.recycler_view_pokemon)
        val adapter = PokedexAdapter((activity?.application as PokemonApplication).imageLoader)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // Infinite scrolling
        recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0){
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount

                    // Als je vijftien items verwijderd bent van de laatste zullen de volgende 20 pokemon opgehaald worden
                    if((visibleItemCount + pastVisibleItems) >= (total - 15)){
                        pokedexViewModel.getNextPokemon()
                    }
                }
            }
        })

        // Om de live data bij te houden
        pokedexViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon.isNotEmpty()) {
                pokemon.let { adapter.submitList(ArrayList(it)) }
            }
        }

        return root
    }
}