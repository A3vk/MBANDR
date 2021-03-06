package com.example.mypokedexapp.ui.pokedex

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedexapp.R

class PokedexFragment : Fragment(), PokedexAdapter.OnItemClickListener {

    private lateinit var pokedexViewModel: PokedexViewModel
    private lateinit  var layoutManager: LinearLayoutManager
    private lateinit var adapter: PokedexAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pokedexViewModel = ViewModelProvider(this).get(PokedexViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pokedex, container, false)
        val recyclerview: RecyclerView = root.findViewById(R.id.recycler_view_pokemon)

        pokedexViewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PokedexAdapter(pokemon, this)
            recyclerview.also{
                it.layoutManager = layoutManager
                it.setHasFixedSize(true)
                it.adapter = adapter
            }
        })

        recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0){
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount

                    if((visibleItemCount + pastVisibleItems) >= (total - 15)){
                        pokedexViewModel.getNextPokemon()
                    }
                }
            }
        })
        return root
    }

    override fun onItemClick(position: Int) {
        val clickedPokemon = pokedexViewModel.pokemon.value?.get(position)
        findNavController().navigate(R.id.action_navigation_pokedex_to_pokemonDetailFragment, bundleOf("pokemonId" to clickedPokemon?.number))
    }
}