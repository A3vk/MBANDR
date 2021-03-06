package com.example.mypokedexapp.room.dao

import androidx.room.*
import com.example.mypokedexapp.models.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)

    @Delete
    suspend fun deletePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): Array<Pokemon>

    @Query("SELECT * FROM pokemon WHERE number < 0")
    suspend fun getAllCustomPokemon(): Array<Pokemon>

    @Query("SELECT * FROM pokemon WHERE is_in_team = 1")
    suspend fun getPokemonTeam(): Array<Pokemon>

    @Query("SELECT * FROM pokemon WHERE number = :number LIMIT 1")
    suspend fun getPokemon(number: Int): Pokemon?
}