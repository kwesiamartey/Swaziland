package com.swaziland_radio.recyclerview

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.swaziland_radio.PlayerFragment
import com.swaziland_radio.R
import com.swaziland_radio.room.MyRoomEntity
import com.swaziland_radio.room.ViewModel
import kotlinx.android.synthetic.main.activity_favourite_list_items.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteRecyclerview(val context: Context, val entity : MutableList<MyRoomEntity>, val owner:ViewModelStoreOwner) : RecyclerView.Adapter<FavouriteRecyclerview.MyViewHolder>(){

     val viewModel by lazy {
         ViewModelProvider(owner).get(ViewModel::class.java)
     }

    inner class MyViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView){

        fun setData(entity:MyRoomEntity, position: Int){
            itemView.name.text = entity.name.toString()
            itemView.description.text = "Portugal - Radio"
        }

        fun selectFav(entity:MyRoomEntity, position: Int){
            itemView.selected_fav.setOnClickListener {
                viewModel.title = entity.name
                viewModel.playerUrl = entity.urlLink
                viewModel.desc = "Enjoy the best radio nigeria app"
                (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(android.R.id.content, PlayerFragment()).commit()
            }
            itemView.delete.setOnClickListener {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle("Remove Station")
                alertDialog.setMessage("Are you sure you want to remove the station?")
                alertDialog.setNegativeButton("Cancel"){_,_->

                }
                alertDialog.setPositiveButton("Remove Station"){_,_->
                    GlobalScope.launch {
                        viewModel.deletRepository(entity)
                    }
                }
                alertDialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_favourite_list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hold = entity[position]
        holder.setData(hold, position)
        holder.selectFav(hold, position)
        //holder.clickable(hold, position)
    }

    override fun getItemCount(): Int {
        return entity.size
    }
}