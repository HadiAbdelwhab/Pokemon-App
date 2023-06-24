package com.example.pokemon.model



import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "fav_table"
)
data class Pokemon(
    @PrimaryKey(
        autoGenerate = true
    )
    val id:Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    var url: String
)
