package com.duje.gameapp.data.model

import com.duje.gameapp.data.remote.responses.Genre

data class GameRecyclerViewItemModel(
    val gameName: String,
    val gameId: Int,
    val gameGenre: List<Genre>,
    val gameRating: Double,
    val gameBackgroud: String
)
