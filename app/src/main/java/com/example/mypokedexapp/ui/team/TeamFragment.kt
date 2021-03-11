package com.example.mypokedexapp.ui.team

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.utils.SwipeToDeleteCallback

class TeamFragment : Fragment() {
    private val teamViewModel: TeamViewModel by viewModels {
        TeamViewModelFactory((activity?.application as PokemonApplication).repository)
    }
    var pokemonShareText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.team_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.team_menu_share -> {
                // Maak de share intent
                val intent: Intent = Intent().apply {
                    // Data versturen
                    action = Intent.ACTION_SEND
                    // Dit is de tekst die je wil versturen
                    putExtra(Intent.EXTRA_TEXT, pokemonShareText)
                    // Je wil tekst versturen
                    type = "text/plain"
                }
                // Start het delen van de tekst
                val shareIntent = Intent.createChooser(intent, null)
                startActivity(shareIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_team, container, false)
        val recyclerview = root.findViewById<RecyclerView>(R.id.recycler_view_pokemon)
        val adapter = TeamAdapter((activity?.application as PokemonApplication).imageLoader)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // Het deleten van de pokemon uit het team
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                teamViewModel.removePokemonFromTeam(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)

        teamViewModel.pokemonTeam.observe(viewLifecycleOwner) { pokemon ->
            pokemon.let { adapter.submitList(ArrayList(it)) }

            // Stel de share text in
            if(pokemon.isNotEmpty()) {
                pokemonShareText = getString(R.string.share_intro, getString(R.string.app_name), resources.getStringArray(R.array.share_numbers)[pokemon.count() - 1])
                pokemon.forEach {item ->
                    pokemonShareText += "\t${pokemon.indexOf(item) + 1}) # ${item.number}\t-\t${item.name}\n"
                }
                pokemonShareText += getString(R.string.share_ending)
            } else {
                pokemonShareText = getString(R.string.share_ending)
            }
        }
        return root
    }
}