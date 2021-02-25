package com.swaziland_radio.room

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel(application: Application) :AndroidViewModel(application){

    var title:String?=null
    var desc:String?=null
    var playerUrl:String?=null
    private lateinit var repository:Repository
    var getEnt: LiveData<List<MyRoomEntity>>? = null

    init {
        val getEntityRepositoryDao = RecordingRoomDatabase.invoke(application).getRepositoryDao()
        repository = Repository(getEntityRepositoryDao)
        getEnt = repository.getEntitys
    }

    suspend fun insertRepository(myRoomEntity: MyRoomEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDao(myRoomEntity)
        }
    }

    suspend fun deletRepository(myRoomEntity: MyRoomEntity){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteDao(myRoomEntity)
        }
    }
}