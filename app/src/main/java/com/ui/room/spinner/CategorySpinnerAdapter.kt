package com.ui.room.spinner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ui.R

class CategorySpinnerAdapter(val items : List<Category>) : BaseAdapter(){

    class ViewHolder(val view :View){
        val title : TextView= view.findViewById(R.id.title_Spinner)
        val image : ImageView=view.findViewById(R.id.icon_Spinner)
    }

    override fun getCount(): Int {
      return items.size ?: 0
    }

    override fun getItem(p0: Int): Any {
       return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(index: Int, view: View?, container: ViewGroup?): View {
        var myView = view
        var  viewHolder : ViewHolder
        when (myView){
            null->{
                myView= LayoutInflater.from(container?.context).inflate(R.layout.item_spinner_category , container, false)
         viewHolder = ViewHolder(myView)
                myView.setTag(viewHolder)
            }
            else->{
                viewHolder = myView.tag as ViewHolder
            }
        }
        val itemList = items[index]
        viewHolder.title.setText(itemList.name)
        viewHolder.image.setImageResource(itemList.imageId!!)
        return myView!!
    }
}