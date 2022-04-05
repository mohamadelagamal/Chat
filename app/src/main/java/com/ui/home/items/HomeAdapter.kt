package com.ui.home.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.model.Room
import com.ui.R
import com.ui.databinding.ItemHomeRecycleviewBinding

class HomeAdapter(var items :List<Room>? ) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    lateinit var namesList : List<String>

    class ViewHolder(var viewDataBinding: ItemHomeRecycleviewBinding ):
        RecyclerView.ViewHolder(viewDataBinding.root)
    {
        //  var image :ImageView = viewDataBinding.image
        //var textHolder :TextView = viewDataBinding.text
        fun bind(room:Room){
            viewDataBinding.itemHome=room
            viewDataBinding.invalidateAll()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val viewDataBinding:ItemHomeRecycleviewBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.context),
       R.layout.item_home_recycleview,parent,false)
        return ViewHolder(viewDataBinding)
    }


            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!![position])
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                // position this is number in onBindViewHolder and items[position] this number in list
                onItemClickListener?.onItemClick(position, items!![position])
            }
        }
    }

    // get position in list and details items in room and we used interface because we will use it in homeActivity
    interface OnItemClickListener {
        fun onItemClick(pos: Int, room: Room)
    }

    var onItemClickListener: OnItemClickListener? = null
    fun changData(rooms: List<Room>) {
        items = rooms
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }


    private var superHeroesList = ArrayList<String>()
    //to filter the list
    fun filterList(superHeroNames: ArrayList<String>) {
        this.superHeroesList = superHeroNames
        notifyDataSetChanged()
    }



}