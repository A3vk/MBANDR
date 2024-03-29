package com.example.mypokedexapp.ui.pokemon

import android.graphics.BitmapFactory
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.preference.PreferenceManager
import androidx.fragment.app.viewModels
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.utils.ImageHelper
import com.example.mypokedexapp.values.PreferenceKeys
import java.util.*
import kotlin.math.roundToInt

class PokemonDetailFragment : Fragment() {
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels {
        PokemonDetailViewModelFactory((activity?.application as PokemonApplication).repository)
    }
    private val maxTeamSize = 6
    private var numberOfPokemonInTeam = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pokemon_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.pokemon_detail_menu_add -> {
                if(numberOfPokemonInTeam < maxTeamSize) {
                    pokemonDetailViewModel.addPokemonToTeam()
                    Toast.makeText(activity, getString(R.string.pokemon_detail_toast_positive), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, getString(R.string.pokemon_detail_toast_negative, maxTeamSize), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
        pokemonDetailViewModel.numberOfPokemonInTeam.observe(viewLifecycleOwner) {
            numberOfPokemonInTeam = it
        }

        // Haal de kleuren settings op
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val color = sharedPref.getString(PreferenceKeys.COLOR, PreferenceKeys.DEFAULT_COLOR)

        // Get all View elements
        val imageView: NetworkImageView = root.findViewById(R.id.pokemonImage)
        val customImageView: ImageView = root.findViewById(R.id.custom_pokemon_image)
        val pokemonNameView: TextView = root.findViewById(R.id.pokemonName)
        val pokedexNumberView: TextView = root.findViewById(R.id.pokemonId)
        val primaryTypeView: TextView = root.findViewById(R.id.primaryType)
        val secondaryTypeView: TextView = root.findViewById(R.id.secondaryType)
        val statContainer: LinearLayout = root.findViewById(R.id.pokemonStats)

        pokemonDetailViewModel.setPokemon(arguments?.get("pokemonNumber") as Int)
        pokemonDetailViewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
            if(pokemon.imageUrl.startsWith("http", true)){
                imageView.setImageUrl(pokemon.imageUrl, (activity?.application as PokemonApplication).imageLoader)
            } else{
                val bitMap = ImageHelper.base64ToBitmap(pokemon.imageUrl)
                customImageView.setImageBitmap(bitMap)
            }

            pokemonNameView.text = pokemon.name.capitalize(Locale.ROOT)
            val pokedexNumberString = "# ${pokemon.number}"
            pokedexNumberView.text = pokedexNumberString

            primaryTypeView.text = resources.getString(resources.getIdentifier(pokemon.primaryType.name, "string", context?.packageName)).capitalize(Locale.ROOT)
            primaryTypeView.setBackgroundColor(Color.parseColor(pokemon.primaryType.color))
            if(pokemon.secondaryType != null) {
                secondaryTypeView.text = resources.getString(resources.getIdentifier(pokemon.secondaryType!!.name, "string", context?.packageName)).capitalize(Locale.ROOT)
                secondaryTypeView.setBackgroundColor(Color.parseColor(pokemon.secondaryType!!.color))
            }

            statContainer.children.forEachIndexed { index, view ->
                val linearLayout = view as LinearLayout
                val title = linearLayout[0] as TextView
                val value = linearLayout[1] as TextView
                val valueBar = linearLayout[2] as ProgressBar
                val stat = pokemon.getStatByIndex(index)
                if(stat != null) {
                    title.text = resources.getString(resources.getIdentifier(stat.name, "string", context?.packageName)).capitalize(Locale.ROOT)
                    value.text = stat.value.toString()
                    valueBar.progress = (stat.value / 255.0 * 100).roundToInt()
                    valueBar.progressTintList = ColorStateList.valueOf(Color.parseColor(color))
                }
            }
        })

        return root
    }
}