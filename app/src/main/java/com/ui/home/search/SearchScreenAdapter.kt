package com.ui.home.search
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.model.Room
import com.ui.R
import com.ui.databinding.ItemHomeRecycleviewBinding
import com.ui.databinding.ItemHomeRecycleviewSearchBinding

class SearchScreenAdapter (var mContext:Context, var teacherList:List<Room> ):
    RecyclerView.Adapter<SearchScreenAdapter.ListViewHolder>() {

    class ViewHolder(var viewDataBinding: ItemHomeRecycleviewBinding):
        RecyclerView.ViewHolder(viewDataBinding.root)
    {
        //  var image :ImageView = viewDataBinding.image
        //var textHolder :TextView = viewDataBinding.text
        fun bind(room:Room){
            viewDataBinding.itemHome=room

            viewDataBinding.invalidateAll()
        }

    }

    inner class ListViewHolder(var viewDataBinding:ItemHomeRecycleviewSearchBinding)
        : RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(room:Room){
            viewDataBinding.itemHomeSearch=room
            viewDataBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewDataBinding:ItemHomeRecycleviewSearchBinding=
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_home_recycleview_search,parent,false)

        return ListViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int = teacherList.size

     fun setFilteredList(filteredList: ArrayList<Room>){
             this.teacherList = filteredList
             notifyDataSetChanged()
         }
    fun changData(rooms: List<Room>) {
        teacherList = rooms
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var newList = teacherList[position]
        holder.bind(teacherList[position])
       // holder.nameT.text = newList.name
       // holder.imgT.(newList.imageUrl)
        onItemClickListener.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,teacherList[position])
            }
        }

    }

    var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(pos: Int, room: Room)
    }
}