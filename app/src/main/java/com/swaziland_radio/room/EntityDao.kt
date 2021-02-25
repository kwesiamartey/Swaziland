package com.swaziland_radio.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface EntityDao {

    @Query("SELECT * FROM myroomentity")
    fun entitys() : LiveData<List<MyRoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(myRoomEntity: MyRoomEntity)

    @Delete
    fun delete(myRoomEntity: MyRoomEntity)
}