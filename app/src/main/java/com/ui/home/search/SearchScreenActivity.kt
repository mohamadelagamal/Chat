package com.ui.home.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chat.database.getRooms
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.model.Room
import com.ui.Constant
import com.ui.R
import com.ui.chat.ChatActivity


class SearchScreenActivity: AppCompatActivity() {
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    private lateinit var mTeachers:MutableList<Room>
    private lateinit var listAdapter: SearchScreenAdapter
    lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_screen)
        mRecyclerView = findViewById(R.id.myRecyclerView)
        myDataLoaderProgressBar = findViewById(R.id.myProgressBar)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = SearchScreenAdapter(this,mTeachers)
        mRecyclerView.adapter = listAdapter
        /**set Firebase Database*/
        retriveDatabase()
        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun retriveDatabase() {
        getRooms(
            onFailureListener = {
                Toast.makeText(this,"can't fetch rooms " , Toast.LENGTH_LONG).show()},
            onSuccessListener = {
                    qureySnapshot->
                mTeachers.clear()
                val rooms = qureySnapshot.toObjects(Room::class.java)
                listAdapter.changData(rooms)
//                for (teacherSnapshot in qureySnapshot.){
//                    val upload = qureySnapshot.toObjects(Room::class.java)
//                    upload.key = teacherSnapshot.key
//                    mTeachers.add(upload)
//
//                }

                val db=Firebase.firestore
                db.collection("users").whereEqualTo("etat", 1)
                    .addSnapshotListener(object : EventListener<QuerySnapshot?> {
                        override fun onEvent(
                            value: QuerySnapshot?,
                            error: FirebaseFirestoreException?,
                        ) {
                            for (doc in qureySnapshot) {
                                val user = doc.toObject(Room::class.java)
                                mTeachers.add(user)
                            }
                           // mTeachers(listUsers)
                        }

                    })
                // [END get_multiple]
                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE

            }

        )
        listAdapter.onItemClickListener = object : SearchScreenAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Room) {
                startChatActiviy(room)
            }

        }
    }

    lateinit var itemList:List<Room>
    fun filterList(newText: String) {
        val open = ArrayList<Room>()
        mTeachers.forEach { Teacher->
            if (Teacher.name!!.toLowerCase().contains(newText!!.toLowerCase())){
                open.add(Teacher)
            }
        }
        listAdapter.setFilteredList(open)
        //ListAdapter(requireContext(),open)

    }

    private fun startChatActiviy(room:Room) {
        val intent = Intent(this, ChatActivity::class.java)
        // send data
        intent.putExtra(Constant.EXTRA_ROOM, room)
        startActivity(intent)
    }
}