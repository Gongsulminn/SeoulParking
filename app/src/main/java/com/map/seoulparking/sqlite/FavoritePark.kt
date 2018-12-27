package com.map.seoulparking.sqlite

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "favoritepark")
class FavoritePark constructor(@PrimaryKey var parkingCode: String
                   , @ColumnInfo(name = "parkingName") var parkingName: String?
                   , @ColumnInfo(name = "tel") var tel: String? = "등록된 정보가 없습니다."
                   , @ColumnInfo(name = "addr") var addr: String?){

}

