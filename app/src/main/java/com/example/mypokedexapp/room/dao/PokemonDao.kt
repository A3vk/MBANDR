package com.example.mypokedexapp.room.dao

import androidx.room.*
import com.example.mypokedexapp.models.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)

    @Delete
    suspend fun deletePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE number < 0")
    fun getAllCustomPokemon(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE is_in_team = 1")
    fun getPokemonTeam(): Flow<List<Pokemon>>

    @Query("SELECT COUNT(number) FROM pokemon WHERE is_in_team = 1")
    fun getNumberOfPokemonInTeam(): Flow<Int>

    @Query("SELECT * FROM pokemon WHERE number = :number LIMIT 1")
    suspend fun getPokemon(number: Int): Pokemon

    @Query("SELECT MIN(number) - 1 FROM pokemon")
    fun getNewCustomPokemonId() : Flow<Int>

}