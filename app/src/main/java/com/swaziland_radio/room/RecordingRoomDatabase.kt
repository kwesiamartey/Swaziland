package com.swaziland_radio.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyRoomEntity::class], exportSchema = false, version = 3 )
abstract class RecordingRoomDatabase : RoomDatabase(){

    abstract fun getRepositoryDao() : EntityDao

    companion object{
        @Volatile
        var INSTANCE : RecordingRoomDatabase? =null
        val LOCK = Any()

        fun invoke(context: Context) = INSTANCE
            ?: synchronized(LOCK) {
                INSTANCE
                    ?: roomBuilder(
                        context
                    ).also{
                        INSTANCE = it
                    }
            }

        fun roomBuilder(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RecordingRoomDatabase::class.java,
            "recordingDatabase"
        ).build()
    }
}
