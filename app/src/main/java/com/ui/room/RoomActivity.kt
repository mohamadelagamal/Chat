package com.ui.room

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ui.R
import com.ui.databinding.ActivityRoomBinding
import com.ui.home.HomeActivity
import com.ui.room.spinner.CategorySpinnerAdapter

class RoomActivity: BaseActivity<ActivityRoomBinding, RoomViewModel>() , Navigator{
    lateinit var adapter : CategorySpinnerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tarning = AnimationUtils.loadAnimation(this,R.anim.logintext)
        val image = findViewById(R.id.groupRoom) as ImageView
        image.startAnimation(tarning)
        viewDataBinding.vmRoom=viewModel
        viewModel.navigator=this
        adapter= CategorySpinnerAdapter(viewModel.categories)
        viewDataBinding.spinnerID.adapter=adapter
        viewDataBinding.spinnerID.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.selectedCategory=viewModel.categories[p2]
                viewModel.roomAdded.observe(this@RoomActivity,{
                    it->
                    run {
                        if (it) {
                            MaterialAlertDialogBuilder(this@RoomActivity).setCancelable(false).setMessage("Room Added Successfuly").setPositiveButton("yes")
                            { dialog, which->
                                dialog.dismiss()
                            }.show()
                        }
                    }
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_room
    }

    override fun makeViewModelProvider(): RoomViewModel {
       return ViewModelProvider(this).get(RoomViewModel::class.java)
    }

    override fun backHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }


}