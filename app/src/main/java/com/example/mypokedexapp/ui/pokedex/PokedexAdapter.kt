package com.example.mypokedexapp.ui.pokedex

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.ui.pokedex.PokedexAdapter.PokedexViewHolder
import java.util.*

class PokedexAdapter(private val imageLoader: ImageLoader) : ListAdapter<Pokemon, PokedexViewHolder>(
    PokemonCompacter()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        return PokedexViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, imageLoader)
    }

    class PokedexViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pokemonNumber = 0;
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        private val pokemonImage: NetworkImageView = itemView.findViewById(R.id.pokemonImage)

        init {
            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_navigation_pokedex_to_navigation_pokemon_detail, bundleOf("pokemonNumber" to pokemonNumber))
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(pokemon: Pokemon, imageLoader: ImageLoader) {
            pokemonNumber = pokemon.number
            pokemonName.text = "# ${pokemon.number} ${pokemon.name.capitalize(Locale.ROOT)}"
            pokemonImage.setImageUrl(pokemon.imageUrl, imageLoader)
        }

        companion object {
            fun create(parent: ViewGroup): PokedexViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
                return PokedexViewHolder(view)
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