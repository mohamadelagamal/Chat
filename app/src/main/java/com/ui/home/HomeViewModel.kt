package com.ui.home

import com.base.BaseViewModel

class HomeViewModel : BaseViewModel<Navigator>() {


    fun createRoom(){
        navigator?.createRoom()
    }


}