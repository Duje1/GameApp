package com.duje.gameapp.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.duje.gameapp.R

class GenresRecyclerViewAdapter(private val itemList: List<RecyclerViewItemModel>) : RecyclerView.Adapter<GenresRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.itemView.findViewById<Button>(R.id.btnGenre).text = item.name
        holder.itemView.isSelected = item.isPressed
        val notPressedColor = Color.argb(1,224, 225, 221)
        val pressedColor = Color.argb(1,119, 141, 169)

        holder.itemView.setOnClickListener {
            item.isPressed = !item.isPressed
            //TODO: Make this work
            if(item.isPressed)
                holder.itemView.setBackgroundColor(pressedColor)
            else
                holder.itemView.setBackgroundColor(notPressedColor)

            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
