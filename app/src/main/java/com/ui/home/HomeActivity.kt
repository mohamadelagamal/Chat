package com.ui.home

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.chat.database.getRooms
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.model.Room
import com.ui.Constant
import com.ui.R
import com.ui.chat.ChatActivity
import com.ui.databinding.ActivityHomeBinding
import com.ui.home.items.HomeAdapter
import com.ui.home.search.SearchScreenActivity
import com.ui.login.LoginActivity
import com.ui.room.RoomActivity


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() , Navigator{
    var adapter = HomeAdapter(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmHome=viewModel
        viewModel.navigator=this
        viewDataBinding.recyclerView.adapter=adapter


    }

    override fun getLayoutID(): Int {
        return R.layout.activity_home
    }

    fun getData(){
        getRooms(
            onFailureListener = {
                Toast.makeText(this,"can't fetch rooms " , Toast.LENGTH_LONG).show()},
            onSuccessListener = {
                    qureySnapshot->
                val rooms = qureySnapshot.toObjects(Room::class.java)
                adapter.changData(rooms)
            }

        )
    }
    override fun onStart() {
        super.onStart()
       getData()
        adapter.onItemClickListener = object : HomeAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Room) {
                // send data
                startChatActiviy(room)
            }
        }
        adapter.onItemLongClick = object : HomeAdapter.setOnLongClickListener{
            override fun onItemClickLong(pos: Int, room: Room) {
                val collection = Firebase.firestore.collection(Room.COLLECTION_NAME)
                val id: String = collection.id
                MaterialAlertDialogBuilder(this@HomeActivity).setCancelable(true)
                    .setMessage("Do you want to Delete this Room ?").setPositiveButton("yes")
                { dialog, which ->
                    val db = Firebase.firestore
                    // onItemLongClick?.onItemClickLong(position , items!![position] )
                    db.collection(Room.COLLECTION_NAME).document(room.id.toString())
                        .delete().addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                            Toast.makeText(this@HomeActivity,"successfully deleted",Toast.LENGTH_LONG).show()

                            getData()
                        }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }

                    collection.document(id).delete().addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {

                            }
                            else -> {
                                Log.e("a;fsdljk", "a;sdfjkl;asdfj----------------------------")
                            }
                        }
                        dialog.dismiss()
                    }
                }.show()
            }
        }
    }
    override fun makeViewModelProvider(): HomeViewModel {
      return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun startChatActiviy(room: Room) {
        val intent = Intent(this, ChatActivity::class.java)
           // send data
        intent.putExtra(Constant.EXTRA_ROOM, room)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun createRoom() {
//        val intent = Intent(this,RoomActivity::class.java)
//        startActivity(intent)
        startActivity(Intent(this, RoomActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.shap_search, menu)
        val manager= getSystemService(Context.SEARCH_SERVICE)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView


        return super.onCreateOptionsMenu(menu)
    }
        val auth = FirebaseAuth.getInstance()
    lateinit var snapshot: DataSnapshot
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
            auth.signOut()
//                val intent = Intent(this,LoginActivity::class.java)
//                startActivity(intent)
//                finish()
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
            R.id.search->{
//                val intent = Intent(this@HomeActivity, SearchScreenActivity::class.java)
//                startActivity(intent)
                startActivity(Intent(this, SearchScreenActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)

    }

}