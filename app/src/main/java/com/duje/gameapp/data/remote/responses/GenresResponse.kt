package com.duje.gameapp.data.remote.responses

import com.google.gson.Gson

data class GenresResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GameCategory>
)

data class GameCategory(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String,
    val games: List<Game>
)

data class Game(
    val id: Int,
    val slug: String,
    val name: String,
    val added: Int
)
