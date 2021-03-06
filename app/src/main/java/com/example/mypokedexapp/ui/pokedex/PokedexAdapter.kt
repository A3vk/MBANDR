package com.example.mypokedexapp.ui.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.volley.BackendVolley

class PokedexAdapter : ListAdapter<Pokemon, PokemonViewHolder>(PokemonCompacter()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        private val pokemonImage: NetworkImageView = itemView.findViewById(R.id.pokemonImage)

        fun bind(pokemon: Pokemon) {
            pokemonName.text = pokemon.name
            pokemonImage.setImageUrl(pokemon.imageUrl, BackendVolley.instance?.imageLoader)
        }

        companion object {
            fun create(parent: ViewGroup): PokemonViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_pokemon, parent, false)
                return PokemonViewHolder(view)
            }
        }
    }

    class PokemonCompacter : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.number == newItem.number
        }
    }
}