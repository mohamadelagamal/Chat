package com.model


import android.os.Parcelable
import com.ui.room.spinner.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id : String ?=null,
    var name:String?=null,
    var categoryId : String?=null,
    var description : String?=null
): Parcelable {
    companion object{
        var COLLECTION_NAME = "rooms"
    }

    fun getCategoryImageID(): Int? {
        return Category.itemRoomShow(categoryId ?: Category.Sports).imageId
    }
    fun getNameRoom():String{
        return name.toString()
    }
}
