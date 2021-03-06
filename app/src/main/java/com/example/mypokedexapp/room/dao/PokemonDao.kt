package com.example.mypokedexapp.room.dao

import androidx.room.*
import com.example.mypokedexapp.models.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPokemon(pokemon: Pokemon)

    @Update
    fun updatePokemon(pokemon: Pokemon)

    @Delete
    fun deletePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): Array<Pokemon>

    @Query("SELECT * FROM pokemon WHERE number < 0")
    fun getAllCustomPokemon(): Array<Pokemon>

    @Query("SELECT * FROM pokemon WHERE is_in_team = 1")
    fun getPokemonTeam(): Array<Pokemon>

    @Query("SELECT * FROM pokemon WHERE number = :number LIMIT 1")
    fun getPokemon(number: Int): Pokemon?
}