package com.swaziland_radio.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.swaziland_radio.AdonWebPlayerFragment
import com.swaziland_radio.databinding.ActivityRecyclerviewListBinding
import com.swaziland_radio.PlayerFragment
import com.swaziland_radio.data.RadioData
import com.swaziland_radio.room.ViewModel

class ListOfRadioStationRecyclerview(val context: Context, val radioList : List<RadioData>, val viewModelStoreOwner: ViewModelStoreOwner ) :
    RecyclerView.Adapter<ListOfRadioStationRecyclerview.MyViewHolder>(){
    inner class MyViewHolder(val binding : ActivityRecyclerviewListBinding) : RecyclerView.ViewHolder(binding.root){
        val viewModel by lazy {
            ViewModelProvider(viewModelStoreOwner).get(ViewModel::class.java)
        }


        fun setData(radioLists:RadioData,  position: Int){
            radioLists?.let {
                binding.radioList = it
            }
        }

        fun clicked(radioLists:RadioData,  position: Int){
            radioLists?.let { me->
                binding.stationImg.setOnClickListener {
                    viewModel.title = me!!.name
                    viewModel.playerUrl = me!!.url
                    viewModel.desc = me!!.description.toString()
                    if(
                       me.name == "Hala Radio" ||
                       me.name == "TWR Africa" ||
                       me.name == "Voice of the Church FM - VOC 2 FM English Channel" ||
                       me.name == "Voice of the Church FM - VOCFM" ||
                       me.name == "Radio Carrum" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == "" ||
                       me.name == ""

                    ){
                        (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(android.R.id.content, PlayerFragment()).commit()
                    }else{
                        (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(android.R.id.content, AdonWebPlayerFragment()).commit()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ActivityRecyclerviewListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hold = radioList.get( position)
        holder.setData(hold, position)
        holder.clicked(hold, position)
    }

    override fun getItemCount(): Int {
        return radioList.size
    }

}


