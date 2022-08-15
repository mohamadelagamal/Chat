package com.ui.room

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.base.BaseViewModel
import com.chat.database.addRoom
import com.model.Room
import com.ui.room.spinner.Category

class RoomViewModel: BaseViewModel<Navigator>() {

    var roomName = ObservableField<String>()
    var roomDescription =ObservableField<String>()
    var roomNameError = ObservableField<String>()
    var roomDescriptionError =ObservableField<String>()
    var categories = Category.getCategoriesList()
    var selectedCategory = categories[0]
    // to now this items added successfully
        val roomAdded = MutableLiveData<Boolean>()
    fun backHome(){
        navigator?.backHome()
    }
    fun createRoom(){
        when {
            VaildAtion() -> {
                val room =
                    Room(name=roomName.get(), description = roomDescription.get(),
                        categoryId = selectedCategory.id)
                // send room to firebaseUtils
                showLoading.value=true
                addRoom(room, onSuccessListener = {
                    showLoading.value=false
                    roomAdded.value=true
                    navigator?.backHome()
                }, onFailureListener = {
                    showLoading.value=false
                    messageLiveData.value=it.localizedMessage
                })
            }
        }
    }
    fun VaildAtion(): Boolean{
        var valid = true
        if (roomName.get().isNullOrBlank()){
            roomNameError.set("Please enter Room Name")
            valid=false
        }
        else{
            roomNameError.set(null)
        }
        if (roomDescription.get().isNullOrBlank()){
            roomDescriptionError.set("Please enter Room Description")
            valid=false
        }
        else{
            roomDescriptionError.set(null)
        }
        return valid
    }


}