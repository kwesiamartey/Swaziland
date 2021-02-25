package com.swaziland_radio.room

import androidx.lifecycle.LiveData

class Repository(val entityDao: EntityDao){

    val getEntitys:LiveData<List<MyRoomEntity>> = entityDao.entitys()

    suspend fun insertDao(myRoomEntity: MyRoomEntity){
        entityDao.insert(myRoomEntity)
    }

    suspend fun deleteDao(myRoomEntity: MyRoomEntity){
        entityDao.delete(myRoomEntity)
    }
}