package com.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.model.Room
import com.ui.Constant
import com.ui.R
import com.ui.databinding.ActivityChatBinding
import com.ui.home.HomeActivity

class ChatActivity : BaseActivity<ActivityChatBinding,ChatViewModel>(),Navigator {
    lateinit var room:Room
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constant.EXTRA_ROOM)!!
        viewModel.room = room
        viewDataBinding.vmRoomChat = viewModel
        viewModel.navigator = this
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_chat
    }

    override fun makeViewModelProvider(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    override fun backHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)

    }
}