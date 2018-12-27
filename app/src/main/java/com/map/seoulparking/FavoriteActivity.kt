package com.map.seoulparking

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.map.seoulparking.adapter.FavoritAdapter
import com.map.seoulparking.databinding.ActivityFavoriteBinding
import com.map.seoulparking.sqlite.AppDataBase
import com.map.seoulparking.sqlite.FavoritePark

/**
 * Created by user on 2018-12-23.
 */
class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    lateinit var favoriteModelList: List<FavoritePark>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@FavoriteActivity , R.layout.activity_favorite)

        var handler: Handler = @SuppressLint("HandlerLeak")
        object: Handler(){
            override fun handleMessage(msg: Message?) {
               when(msg?.what){
                   0 -> {
                       var adapter: FavoritAdapter = FavoritAdapter(this@FavoriteActivity , favoriteModelList)
                       binding.favoriteRecycler.layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL,false)
                       binding.favoriteRecycler.adapter = adapter
                   }
               }
            }
        }

        Thread(Runnable {
            favoriteModelList = AppDataBase.getInstance(this)!!.parkDao().getAll()
            handler.sendEmptyMessage(0)
        }).start()
    }
}