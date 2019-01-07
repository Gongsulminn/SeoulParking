package com.map.seoulparking.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.map.seoulparking.R
import com.map.seoulparking.databinding.ItemFavoriteBinding
import com.map.seoulparking.sqlite.FavoritePark

class FavoritAdapter(val context: Context , val favoritModelList: List<FavoritePark>) : RecyclerView.Adapter<FavoritAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent , false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return favoritModelList.size
    }

    override fun onBindViewHolder(viewholder: ViewHolder, postion: Int) {
        viewholder.binding.favoritemodel = favoritModelList[postion]
    }


     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var binding: ItemFavoriteBinding
         init {
            binding = DataBindingUtil.bind(itemView)!!
         }
    }

}