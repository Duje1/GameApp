package com.duje.gameapp.utils

import com.duje.gameapp.data.model.GameRecyclerViewItemModel


var firstLogin: Boolean = true
var gameId: Int = 0
var YOUR_API = "YOUR_API"

var loadedGames: MutableList<GameRecyclerViewItemModel> = mutableListOf()
var pageNumber: Int = 1