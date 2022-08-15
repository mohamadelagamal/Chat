package com.ui.chat

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.model.DataUtil
import com.base.BaseViewModel
import com.chat.database.addMessage
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.model.Messages
import com.model.Room
import java.util.*

class ChatViewModel : BaseViewModel<Navigator>() {

        var room : Room?=null
    var toastMessage = MutableLiveData<String>()
    var contentMessage =  ObservableField<String>()
    fun sendMessage(){
        val message = Messages(
            content = contentMessage.get(),
            roomID = room?.id,
            senderId = DataUtil.user?.id,
            senderName = DataUtil.user?.userName,
            dateTime = Date().time
        )
        addMessage(message, OnSuccessListener {
            contentMessage.set("")
        }, OnFailureListener {
            Log.e("message",it.localizedMessage)

            // toastMessage.value = "Something went wrong , try again"
        })
        // go to login activity
    }


    fun backHome(){
    navigator?.backHome()
    }
}