package com.example.mypokedexapp.ui.profile

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.example.mypokedexapp.ui.profile.ProfileAdapter.ProfileViewHolder

class ProfileAdapter(private val imageLoader: ImageLoader) : ListAdapter<Pokemon, ProfileViewHolder>(
    PokemonCompacter()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, imageLoader)
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pokemonNumber = 0;
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        private val pokemonImage: NetworkImageView = itemView.findViewById(R.id.pokemonImage)

        init {
            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_navigation_profile_to_navigation_pokemon_detail, bundleOf("pokemonNumber" to pokemonNumber))
            }
        }

        fun bind(pokemon: Pokemon, imageLoader: ImageLoader) {
            pokemonNumber = pokemon.number
            pokemonName.text = "# ${pokemon.number} ${pokemon.name}"

            val base64 = pokemon.imageUrl
            println(pokemon.imageUrl)
            val decodedString = Base64.decode(base64.substring(base64.indexOf(",") + 1), Base64.DEFAULT);
            val bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            pokemonImage.setImageBitmap(bitMap)
            val testImage = itemView.findViewById<ImageView>(R.id.custom_pokemon_image)
            testImage.setImageBitmap(bitMap)
        }

        companion object {
            fun create(parent: ViewGroup): ProfileViewHolder{
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
                return ProfileViewHolder(view)
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



