package com.example.zotfit.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.zotfit.R
import com.fatsecret.platform.model.CompactFood

class FoodAdapter internal constructor(
        context: Context, val click: onItemClick
) : Adapter<FoodAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemsList: MutableList<CompactFood> = mutableListOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView)
        fun initialize(food: CompactFood, action: onItemClick){
            itemView.setOnClickListener{
                action.onItemClick(food, adapterPosition)
            }
        }

    }
    fun setWords(items: List<CompactFood>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.searchitem, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = itemsList[position]
        holder.name.text=current.name
        holder.initialize(itemsList[position], click)
    }


    override fun getItemCount(): Int {
        return itemsList.size
    }
}

interface onItemClick{
    fun onItemClick(itemsList: CompactFood, position: Int)
}