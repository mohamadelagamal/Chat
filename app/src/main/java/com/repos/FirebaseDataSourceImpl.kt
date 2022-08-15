package com.repos

import com.chat.database.addUserToFireStore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.model.ApplicationUser
import com.model.DataUtil

class FirebaseDataSourceImpl:FirebaseRepository {

    override  fun addUser(appUser:ApplicationUser) {

        addUserToFireStore(appUser ,
            OnSuccessListener {
              //  showLoading.value=false
                DataUtil.user = appUser
               // navigator?.openHome()
            }, OnFailureListener {
              //  showLoading.value=false
              //  messageLiveData.value=it.localizedMessage
            })
    }

}