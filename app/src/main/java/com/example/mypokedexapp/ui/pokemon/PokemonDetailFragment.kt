package com.example.mypokedexapp.ui.pokemon

import android.content.SharedPreferences
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
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.preference.PreferenceManager
import com.android.volley.toolbox.NetworkImageView
import com.example.mypokedexapp.R
import com.example.mypokedexapp.volley.BackendVolley
import java.util.*
import kotlin.math.roundToInt

class PokemonDetailFragment : Fragment(){

    private lateinit var viewModel: PokemonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val color = sharedPref.getString("color_preference", "#000000")

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
                    textView.text = getPokemonTypeName(type.name, sharedPref).capitalize(Locale.ROOT)
                    textView.setBackgroundColor(Color.parseColor(type.color))
                }
            }

            statContainer.children.forEachIndexed { index, view ->
                val linearLayout = view as LinearLayout
                val title = linearLayout[0] as TextView
                val value = linearLayout[1] as TextView
                val valueBar = linearLayout[2] as ProgressBar
                val stat = pokemon.stats[index]
                title.text = resources.getString(resources.getIdentifier("hp", "string", context?.packageName))
                value.text = stat.value.toString()
                valueBar.progress = (stat.value / 255.0 * 100).roundToInt()
                val colorFilter=  BlendModeColorFilterCompat.createBlendModeColorFilterCompat(Color.parseColor(color), BlendModeCompat.SRC_ATOP)
                valueBar.progressDrawable.colorFilter = colorFilter
            }
        })

        return root
    }

//    private fun getPokemonStatNameEn(stat: String) :String{
//        return when (stat){
//            "hp" -> resources.getString(R.string.stat_hp_en)
//            "attack" -> resources.getString(R.string.stat_attack_en)
//            "defense" -> resources.getString(R.string.stat_defense_en)
//            "special-attack" -> resources.getString(R.string.stat_special_attack_en)
//            "special-defense" -> resources.getString(R.string.stat_special_defense_en)
//            "speed" -> resources.getString(R.string.stat_speed_en)
//            else -> ""
//        }
//    }

    private fun getPokemonStatNameNl(stat: String) :String{
        return when (stat){
            "hp" -> resources.getString(R.string.stat_hp_nl)
            "attack" -> resources.getString(R.string.stat_attack_nl)
            "defense" -> resources.getString(R.string.stat_defense_nl)
            "special-attack" -> resources.getString(R.string.stat_special_attack_nl)
            "special-defense" -> resources.getString(R.string.stat_special_defense_nl)
            "speed" -> resources.getString(R.string.stat_speed_nl)
            else -> ""
        }
    }

    private fun getPokemonStatName(stat: String, sharedPreferences: SharedPreferences): String{
        return when (sharedPreferences.getString("language_preference", "EN")){
//            "EN" -> return getPokemonStatNameEn(stat)
            "NL" -> return getPokemonStatNameNl(stat)
            else -> ""
        }
    }

    private fun getPokemonTypeNameEn(stat: String) :String{
        return when(stat){
            "water" -> resources.getString(R.string.type_water_en)
            "fire" -> resources.getString(R.string.type_fire_en)
            "grass" -> resources.getString(R.string.type_grass_en)
            "ground" -> resources.getString(R.string.type_ground_en)
            "rock" -> resources.getString(R.string.type_rock_en)
            "steel" -> resources.getString(R.string.type_steel_en)
            "ice" -> resources.getString(R.string.type_ice_en)
            "electric" -> resources.getString(R.string.type_electric_en)
            "dragon" -> resources.getString(R.string.type_dragon_en)
            "ghost" -> resources.getString(R.string.type_ghost_en)
            "psychic" -> resources.getString(R.string.type_psychic_en)
            "normal" -> resources.getString(R.string.type_normal_en)
            "fighting" -> resources.getString(R.string.type_fighting_en)
            "poison" -> resources.getString(R.string.type_poison_en)
            "bug" -> resources.getString(R.string.type_bug_en)
            "flying" -> resources.getString(R.string.type_flying_en)
            "dark" -> resources.getString(R.string.type_dark_en)
            "fairy" -> resources.getString(R.string.type_fairy_en)
            else -> ""
        }
    }

    private fun getPokemonTypeNameNl(stat: String) :String{
        return when(stat){
            "water" -> resources.getString(R.string.type_water_nl)
            "fire" -> resources.getString(R.string.type_fire_nl)
            "grass" -> resources.getString(R.string.type_grass_nl)
            "ground" -> resources.getString(R.string.type_ground_nl)
            "rock" -> resources.getString(R.string.type_rock_nl)
            "steel" -> resources.getString(R.string.type_steel_nl)
            "ice" -> resources.getString(R.string.type_ice_nl)
            "electric" -> resources.getString(R.string.type_electric_nl)
            "dragon" -> resources.getString(R.string.type_dragon_nl)
            "ghost" -> resources.getString(R.string.type_ghost_nl)
            "psychic" -> resources.getString(R.string.type_psychic_nl)
            "normal" -> resources.getString(R.string.type_normal_nl)
            "fighting" -> resources.getString(R.string.type_fighting_nl)
            "poison" -> resources.getString(R.string.type_poison_nl)
            "bug" -> resources.getString(R.string.type_bug_nl)
            "flying" -> resources.getString(R.string.type_flying_nl)
            "dark" -> resources.getString(R.string.type_dark_nl)
            "fairy" -> resources.getString(R.string.type_fairy_nl)
            else -> ""
        }
    }

    private fun getPokemonTypeName(stat: String, sharedPreferences: SharedPreferences) :String{
        return when (sharedPreferences.getString("language_preference", "EN")){
            "EN" -> return getPokemonTypeNameEn(stat)
            "NL" -> return getPokemonTypeNameNl(stat)
            else -> ""
        }
    }
}