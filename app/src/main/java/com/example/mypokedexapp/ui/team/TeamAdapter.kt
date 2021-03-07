package com.example.mypokedexapp.ui.team

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
import com.example.mypokedexapp.ui.team.TeamAdapter.TeamViewHolder
import java.util.*

class TeamAdapter(private val imageLoader: ImageLoader) : ListAdapter<Pokemon, TeamViewHolder>(
    PokemonCompacter()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, imageLoader)
    }

    fun removeAt(position: Int) {
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pokemonNumber = 0;
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        private val pokemonImage: NetworkImageView = itemView.findViewById(R.id.pokemonImage)

        init {
            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_navigation_team_to_navigation_pokemon_detail, bundleOf("pokemonNumber" to pokemonNumber))
            }
        }

        fun bind(pokemon: Pokemon, imageLoader: ImageLoader) {
            pokemonNumber = pokemon.number
            pokemonName.text = "# ${pokemon.number} ${pokemon.name.capitalize(Locale.ROOT)}"
            pokemonImage.setImageUrl(pokemon.imageUrl, imageLoader)
        }

        companion object {
            fun create(parent: ViewGroup): TeamViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
                return TeamViewHolder(view)
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