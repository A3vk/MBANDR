package com.example.mypokedexapp.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject

@Entity (tableName = "pokemon")
data class Pokemon(
    @PrimaryKey var number: Int,
    var name: String,
    @ColumnInfo(name = "image_url") var imageUrl: String,
    var hp: Int,
    var attack: Int,
    var defence: Int,
    @ColumnInfo(name = "special_attack") var specialAttack: Int,
    @ColumnInfo(name = "special_defence") var specialDefence: Int,
    var speed: Int,
    @Embedded(prefix = "primary_") var primaryType: Type,
    @Embedded(prefix = "secondary_") var secondaryType: Type?,
    @ColumnInfo(name = "is_in_team") var isInTeam: Boolean = false
) : Comparable<Pokemon> {
    companion object {
        fun fromJson(json: JSONObject): Pokemon {
            val jsonStats = json.getJSONArray("stats")
            val jsonTypes = json.getJSONArray("types")
            val secondary: Type? = if(jsonTypes.length() > 1)  Type.fromJson(jsonTypes.getJSONObject(1)) else null
            return Pokemon(
                json.getInt("id"),
                json.getString("name"),
                json.getJSONObject("sprites").getString("front_default"),
                jsonStats.getJSONObject(0).getInt("base_stat"),
                jsonStats.getJSONObject(1).getInt("base_stat"),
                jsonStats.getJSONObject(2).getInt("base_stat"),
                jsonStats.getJSONObject(3).getInt("base_stat"),
                jsonStats.getJSONObject(4).getInt("base_stat"),
                jsonStats.getJSONObject(5).getInt("base_stat"),
                Type.fromJson(jsonTypes.getJSONObject(0)),
                secondary
            )
        }
    }

    override fun compareTo(other: Pokemon): Int {
        return number - other.number
    }

    fun getStatByIndex(index: Int): Stat? {
        return when(index) {
            0 -> Stat("hp", hp)
            1 -> Stat("attack", attack)
            2 -> Stat("defence", defence)
            3 -> Stat("special attack", specialAttack)
            4 -> Stat("special defence", specialDefence)
            5 -> Stat("speed", speed)
            else -> null
        }
    }
}