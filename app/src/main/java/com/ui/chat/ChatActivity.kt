package com.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BaseActivity
import com.chat.database.getMessageRef
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.model.Messages
import com.model.Room
import com.ui.Constant
import com.ui.R
import com.ui.chat.messages.MessagesAdapter
import com.ui.databinding.ActivityChatBinding
import com.ui.home.HomeActivity

class ChatActivity : BaseActivity<ActivityChatBinding,ChatViewModel>(),Navigator {
      lateinit var room:Room
    // to sorted messages
    lateinit var layoutManager: LinearLayoutManager
    val adapter = MessagesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constant.EXTRA_ROOM)!!
        viewModel.room = room
        viewDataBinding.vmRoomChat = viewModel
        viewModel.navigator = this
        viewDataBinding.recyclerView.adapter=adapter
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd=true
        viewDataBinding.recyclerView.layoutManager=layoutManager
        listForMessagesUpdates()
    }

    fun listForMessagesUpdates(){
        getMessageRef(roomId = room.id!!)
            .orderBy("dateTime",Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
            if (error!=null){
             //   Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
            }else {
                val newMessagesList = mutableListOf<Messages>()
                for (dc in snapshot!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            val message = dc.document.toObject(Messages::class.java)
                            newMessagesList.add(message)
                        }
                    }
                }
                 adapter.appendMessages(newMessagesList)
                viewDataBinding.recyclerView.smoothScrollToPosition(adapter.itemCount)
            }}
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_chat
    }

    override fun makeViewModelProvider(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    override fun backHome() {
//        val intent = Intent(this,HomeActivity::class.java)
//        startActivity(intent)
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }
}