package com.example.mypokedexapp.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.models.Type

class PokemonCreateFragment : Fragment() {
    private val pokemonCreateViewModel: PokemonCreateViewModel by viewModels {
        PokemonCreateViewModelFactory((activity?.application as PokemonApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("HALLO IN BEN HET FRAGMENT")
        val root = inflater.inflate(R.layout.fragment_pokemon_create, container, false)
        val saveButton: Button = root.findViewById(R.id.pokemon_save_button)
        saveButton.setOnClickListener{
            println("IK BEN EEN BUTTON")
            pokemonCreateViewModel.saveCustomPokemon(createPokemon(root))
        }
        return root
    }

    private fun createPokemon(root: View) : Pokemon {
        //TODO implement types
        //TODO implement images
        val image = "https://ucarecdn.com/68712676-976b-4966-92d8-b16694fcf261/anime-fans-nederland-amelia.png"

        val id = pokemonCreateViewModel.nextCustomPokemonId.value!!
        val name = root.findViewById<EditText>(R.id.pokemon_name_input).text.toString()
        val hp = root.findViewById<EditText>(R.id.pokemon_hp_input).text.toString().toInt()
        val attack = root.findViewById<EditText>(R.id.pokemon_attack_input).text.toString().toInt()
        val defence = root.findViewById<EditText>(R.id.pokemon_defence_input).text.toString().toInt()
        val specialAttack = root.findViewById<EditText>(R.id.pokemon_special_attack_input).text.toString().toInt()
        val specialDefence = root.findViewById<EditText>(R.id.pokemon_special_defence_input).text.toString().toInt()
        val speed = root.findViewById<EditText>(R.id.pokemon_speed_input).text.toString().toInt()

        return Pokemon(id, name, image, hp, attack, defence, specialAttack, specialDefence, speed, Type("dark", "#705746"), null, false  )
    }
}