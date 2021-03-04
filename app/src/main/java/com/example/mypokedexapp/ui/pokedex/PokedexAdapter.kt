package com.example.mypokedexapp.ui.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.volley.BackendVolley

class PokedexAdapter (private val pokemon: ArrayList<Pokemon>) : RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>() {
    private var imageLoader: ImageLoader? = BackendVolley.instance?.imageLoader
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.pokemonName.text = "# ${pokemon[position].id} ${pokemon[position].name}"
        holder.pokemonImage.setImageUrl(pokemon[position].imageUrl, BackendVolley.instance?.imageLoader)
    }

    override fun getItemCount() = pokemon.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        val pokemonImage: NetworkImageView = itemView.findViewById(R.id.pokemonImage)
    }
}