package com.example.zotfit.search
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.zotfit.R
import com.fatsecret.platform.model.CompactFood
import com.fatsecret.platform.model.CompactRecipe

class Recipe_Adapter internal constructor(
        val context: Context, val click: onRecipeClick
) : Adapter<Recipe_Adapter.WordViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemsList: MutableList<CompactRecipe> = mutableListOf()
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.recipe_description)
        val image: ImageView = itemView.findViewById(R.id.recipe_image)
        fun initialize(recipe: CompactRecipe, action: onRecipeClick){
            itemView.setOnClickListener{
                action.onRecipeClick(recipe, adapterPosition)
            }
        }

    }
    fun setWords(items: List<CompactRecipe>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recipe_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = itemsList[position]
        holder.name.text=current.name
            if(current.images.size>=1){
                Glide.with(context).load(current.images.get(0)).centerCrop().into(holder.image);
            }

        holder.initialize(itemsList[position], click)
    }


    override fun getItemCount(): Int {
        return itemsList.size
    }
}

interface onRecipeClick{
    fun onRecipeClick(itemsList: CompactRecipe, position: Int)
}