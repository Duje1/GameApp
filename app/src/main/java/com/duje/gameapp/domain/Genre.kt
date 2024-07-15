package com.duje.gameapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
