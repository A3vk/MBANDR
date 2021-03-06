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
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.ui.pokedex.PokedexViewModel
import com.example.mypokedexapp.ui.pokedex.PokedexViewModelFactory
import com.example.mypokedexapp.volley.BackendVolley
import org.w3c.dom.Text
import java.util.*
import kotlin.math.roundToInt

class PokemonDetailFragment : Fragment() {
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels {
        PokemonDetailViewModelFactory((activity?.application as PokemonApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        // Get all View elements
        val imageView: NetworkImageView = root.findViewById(R.id.pokemonImage)
        val pokemonNameView: TextView = root.findViewById(R.id.pokemonName)
        val pokedexNumberView: TextView = root.findViewById(R.id.pokemonId)
        val primaryTypeView: TextView = root.findViewById(R.id.primaryType)
        val secondaryTypeView: TextView = root.findViewById(R.id.secondaryType)
        val statContainer: LinearLayout = root.findViewById(R.id.pokemonStats)

        pokemonDetailViewModel.setPokemon(arguments?.get("pokemonNumber") as Int)
        pokemonDetailViewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            imageView.setImageUrl(pokemon.imageUrl, (activity?.application as PokemonApplication).imageLoader)
            pokemonNameView.text = pokemon.name.capitalize(Locale.ROOT)
            val pokedexNumberString = "# ${pokemon.number}"
            pokedexNumberView.text = pokedexNumberString

            primaryTypeView.text = pokemon.primaryType.name
            primaryTypeView.setBackgroundColor(Color.parseColor(pokemon.primaryType.color))
            if(pokemon.secondaryType != null) {
                secondaryTypeView.text = pokemon.secondaryType!!.name
                secondaryTypeView.setBackgroundColor(Color.parseColor(pokemon.secondaryType!!.color))
            }

            statContainer.children.forEachIndexed { index, view ->
                val linearLayout = view as LinearLayout
                val title = linearLayout[0] as TextView
                val value = linearLayout[1] as TextView
                val valueBar = linearLayout[2] as ProgressBar
                val stat = pokemon.getStatByIndex(index)
                if(stat != null) {
                    title.text = stat.name
                    value.text = stat.value.toString()
                    valueBar.progress = (stat.value / 255.0 * 100).roundToInt()
                }
            }
        })

        return root
    }
}