package com.duje.gameapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duje.gameapp.R
import com.duje.gameapp.data.model.GameRecyclerViewItemModel
import com.duje.gameapp.data.viewModel.OnItemChangedListener

class GamesRecyclerViewAdapter(
    private var gameList: MutableList<GameRecyclerViewItemModel>
) : RecyclerView.Adapter<GamesRecyclerViewAdapter.GameViewHolder>() {

    private var onItemChangedListener: OnItemChangedListener? = null

    fun setOnItemChangedListener(listener: OnItemChangedListener) {
        this.onItemChangedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.games_card, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.bind(game)

        holder.itemView.setOnClickListener() {

            onItemChangedListener?.onItemChanged(position)
            notifyItemChanged(position)
        }

    }

    fun getItem(position: Int): GameRecyclerViewItemModel {
        return gameList[position]
    }

    override fun getItemCount(): Int = gameList.size

    fun addGames(newGames: List<GameRecyclerViewItemModel>) {
        val startPosition = gameList.size
        gameList.addAll(newGames)
        notifyItemRangeInserted(startPosition, newGames.size)
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameBackground: ImageView = itemView.findViewById(R.id.ivGameBackground)
        private val gameName: TextView = itemView.findViewById(R.id.tvGameTitle)
        private val gameGenre: TextView = itemView.findViewById(R.id.tvGameGenre)
        private val gameRating: TextView = itemView.findViewById(R.id.tvGameRating)

        fun bind(game: GameRecyclerViewItemModel) {

            gameName.text = game.gameName
            gameGenre.text = game.gameGenre.joinToString(", ") { it.name }
            gameRating.text = "Rating: ${game.gameRating}/5"


            Glide.with(itemView.context)
                .load(game.gameBackgroud)
                .into(gameBackground)
        }
    }
}
