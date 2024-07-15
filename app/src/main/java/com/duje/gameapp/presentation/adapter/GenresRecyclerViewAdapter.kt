package com.duje.gameapp.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.duje.gameapp.R
import com.duje.gameapp.data.model.RecyclerViewItemModel
import com.duje.gameapp.data.viewModel.OnItemChangedListener

class GenresRecyclerViewAdapter(private var itemList: List<RecyclerViewItemModel>) : RecyclerView.Adapter<GenresRecyclerViewAdapter.ViewHolder>() {



    private var onItemChangedListener: OnItemChangedListener? = null

    fun setOnItemChangedListener(listener: OnItemChangedListener) {
        this.onItemChangedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        holder.itemView.findViewById<Button>(R.id.btnGenre).setOnClickListener {
            item.isPressed = !item.isPressed

            onItemChangedListener?.onItemChanged(position)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getItem(position: Int): RecyclerViewItemModel {
        return itemList[position]
    }

    fun updateItems(newItems: List<RecyclerViewItemModel>) {
        itemList = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnGenre: Button = itemView.findViewById(R.id.btnGenre)

        fun bind(item: RecyclerViewItemModel) {
            btnGenre.text = item.name
            itemView.findViewById<Button>(R.id.btnGenre).setBackgroundColor(if (item.isPressed) Color.argb(255, 119, 141, 169) else Color.argb(255, 224, 225, 221))
        }
    }
}
