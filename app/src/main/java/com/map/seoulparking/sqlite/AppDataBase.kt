package com.map.seoulparking.sqlite

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by user on 2018-12-22.
 */
@Database(entities = [FavoritePark::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
     abstract fun parkDao() : ParkDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDataBase?{
            if(INSTANCE == null){
                synchronized(AppDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDataBase::class.java,"favoritepark.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstace(){
            INSTANCE = null
        }
    }
}