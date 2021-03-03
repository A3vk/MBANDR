package com.example.mypokedexapp.ui.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon

class PokedexAdapter (private val pokemon: ArrayList<Pokemon>) : RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.pokemonId.text = pokemon[position].id.toString()
    }

    override fun getItemCount() = pokemon.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonId: TextView = itemView.findViewById(R.id.pokemonId)
    }
}