package com.swaziland_radio.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyRoomEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val name:String?=null,
    val urlLink:String?=null
)