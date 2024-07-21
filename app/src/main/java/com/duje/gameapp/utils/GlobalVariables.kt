package com.duje.gameapp.utils

import com.duje.gameapp.data.model.GameRecyclerViewItemModel


var firstLogin: Boolean = true
var gameId: Int = 0
var YOUR_API = "17e1938ed62248b19c0399eb1a1f43da"

var loadedGames: MutableList<GameRecyclerViewItemModel> = mutableListOf()
var pageNumber: Int = 1