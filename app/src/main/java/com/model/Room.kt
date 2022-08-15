package com.model


import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.ui.room.spinner.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id : String ?=null,
    var name:String?=null,
    var categoryId : String?=null,
    var description : String?=null,
    @get:Exclude
    @set:Exclude
    var key:String? = null

): Parcelable {
    companion object{
        var COLLECTION_NAME = "rooms"
    }

    fun getCategoryImageID(): Int? {
        return Category.itemRoomShow(categoryId ?: Category.Sports).imageId
    }
}
