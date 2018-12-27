package com.map.seoulparking.adapter

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.map.seoulparking.sqlite.FavoritePark

/**
 * Created by user on 2018-12-23.
 */
object FavoritListBindingAdpater{

    @BindingAdapter("bind:favorititem")
    @JvmStatic
    fun  setFavoriteItem(recyclerview: RecyclerView , favoriteModelList: List<FavoritePark>  ): Unit{
        var favoriteAapter: FavoritAdapter = recyclerview.adapter as FavoritAdapter

    }
}