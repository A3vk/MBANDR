package com.example.mypokedexapp.ui.team

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.mypokedexapp.R
import com.example.mypokedexapp.ui.pokedex.PokedexViewModel

class TeamFragment : Fragment() {

    private lateinit var teamViewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        teamViewModel =
            ViewModelProvider(this).get(TeamViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_team, container, false)
        val textView: TextView = root.findViewById(R.id.text_team)
        teamViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}