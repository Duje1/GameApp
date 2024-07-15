package com.duje.gameapp.data.viewModel

// Interface listener that allows external components to listen for item selection changes within the RecyclerView
interface OnItemChangedListener {
    fun onItemChanged(position: Int)
}
