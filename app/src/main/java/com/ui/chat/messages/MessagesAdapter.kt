package com.ui.chat.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.DataUtil
import com.model.Messages
import com.ui.R
import com.ui.databinding.ItemMessageRecievedBinding
import com.ui.databinding.ItemMessageSendBinding

class MessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = mutableListOf<Messages>()
    val RECIVED =1
    val SEND =2

    override fun getItemViewType(position: Int): Int {
      val message = items.get(position)
      if (message.senderId == DataUtil.user?.id){
          return SEND
      }
        return RECIVED
    }
    class sendMessageViewHolder(val viewDataBinding:ItemMessageSendBinding):
        RecyclerView.ViewHolder(viewDataBinding.root){
        fun bind(messages: Messages){
            viewDataBinding.vmSend=messages
            viewDataBinding.executePendingBindings()
        }
    }
    class recivedMessageViewHolder(val viewDataBinding: ItemMessageRecievedBinding):
            RecyclerView.ViewHolder(viewDataBinding.root){
              fun bind(messages: Messages){
                  viewDataBinding.vmRecieved=messages
                  viewDataBinding.executePendingBindings()
              }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (RECIVED) {
            viewType -> {
                val itemBinding:ItemMessageRecievedBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_message_recieved , parent,false)
                return recivedMessageViewHolder(itemBinding)
            }
            else -> {
                val itemBinding:ItemMessageSendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_message_send , parent,false)
                return sendMessageViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is sendMessageViewHolder){
            holder.bind(items[position])
        }
         if (holder is recivedMessageViewHolder){
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
       return items.count()
    }

    fun appendMessages(newMessagesList: MutableList<Messages>) {
        items.addAll(newMessagesList)
        notifyItemRangeInserted( items.size+1, newMessagesList.size)
    }
}