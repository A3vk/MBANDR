package com.example.mypokedexapp.ui.pokemon

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.get
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.volley.BackendVolley
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round
import kotlin.math.roundToInt

class PokemonDetailFragment : Fragment() {

    private lateinit var viewModel: PokemonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        // Get all View elements
        val imageView: NetworkImageView = root.findViewById(R.id.pokemonImage)
        val pokemonNameView: TextView = root.findViewById(R.id.pokemonName)
        val pokedexNumberView: TextView = root.findViewById(R.id.pokemonId)
        val typeContainer: LinearLayout = root.findViewById(R.id.pokemonTypes)
        val statContainer: LinearLayout = root.findViewById(R.id.pokemonStats)

        viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)
        viewModel.setPokemon(arguments?.get("pokemonId") as Int)
        viewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            imageView.setImageUrl(pokemon.imageUrl, BackendVolley.instance?.imageLoader)
            pokemonNameView.text = pokemon.name.capitalize(Locale.ROOT)
            val pokedexNumberString = "# ${pokemon.id}"
            pokedexNumberView.text = pokedexNumberString

            typeContainer.children.forEachIndexed { index, view ->
                if (index < pokemon.types.count()) {
                    val textView = view as TextView
                    val type = pokemon.types[index]
                    textView.text = type.name.capitalize(Locale.ROOT)
                    textView.setBackgroundColor(Color.parseColor(type.color))
                }
            }

            statContainer.children.forEachIndexed { index, view ->
                val linearLayout = view as LinearLayout
                val title = linearLayout[0] as TextView
                val value = linearLayout[1] as TextView
                val valueBar = linearLayout[2] as ProgressBar
                val stat = pokemon.stats[index]
                title.text = stat.name
                value.text = stat.value.toString()
                valueBar.progress = (stat.value / 255.0 * 100).roundToInt()
            }
        })

        return root
    }
}