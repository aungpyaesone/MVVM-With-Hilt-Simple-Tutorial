package com.aungpyaesone.hilt_demo_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class Character(

    val created: String,


    val gender: String,

    @PrimaryKey
    val id: Int,

    val image: String,

    val name: String,

    val species: String,

    val status: String,

    val type: String,

    val url: String
) {
}