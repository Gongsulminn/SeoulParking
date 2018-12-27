package com.map.seoulparking.sqlite

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert

@Dao
interface ParkDao {
    @android.arch.persistence.room.Query("Select * from favoritepark")
    fun getAll(): List<FavoritePark>

    @android.arch.persistence.room.Query("Select count(*) from favoritepark where parkingCode = :code")
    fun isFavorite(code: String): Int

    @Insert
    fun insertData(favoritePark: FavoritePark) : Unit

//    @android.arch.persistence.room.Query("delete * from park")
//    fun deleteAll(): Unit
    @Delete
    fun deleteData(favoritePark: FavoritePark) : Unit
}