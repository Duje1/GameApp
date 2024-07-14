package com.duje.gameapp.presentation.adapter

data class RecyclerViewItemModel(
    val id: Int,
    val name: String,
    var isPressed: Boolean = false
)