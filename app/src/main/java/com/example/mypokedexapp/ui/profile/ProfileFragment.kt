package com.example.mypokedexapp.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.utils.SwipeToDeleteCallback

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((activity?.application as PokemonApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileViewModel.addPokemon()
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val recyclerview = root.findViewById<RecyclerView>(R.id.recycler_view_pokemon)
        val adapter = ProfileAdapter((activity?.application as PokemonApplication).imageLoader)
        recyclerview.adapter =adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                profileViewModel.removeCustomPokemon(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)

        profileViewModel.customPokemon.observe(viewLifecycleOwner)  { pokemon ->
            pokemon.let{ adapter.submitList(ArrayList(it)) }
        }
        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.profile_menu_next -> {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_pokemon_create)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}