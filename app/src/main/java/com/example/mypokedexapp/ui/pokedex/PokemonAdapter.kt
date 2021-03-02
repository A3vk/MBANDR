package com.example.mypokedexapp.ui.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.databinding.RecyclerviewPokemonBinding
import com.example.mypokedexapp.models.Pokemon

class PokemonAdapter (
    private val pokemon: List<Pokemon>
    ) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun getItemCount() = pokemon.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_pokemon,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.recyclerviewPokemonBinding.pokemon = this.pokemon[position]
    }


    inner class PokemonViewHolder(
        val recyclerviewPokemonBinding: RecyclerviewPokemonBinding
    ) : RecyclerView.ViewHolder(recyclerviewPokemonBinding.root)

}