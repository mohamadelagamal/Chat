package com.ui.register

import android.util.Log
import androidx.databinding.ObservableField
import com.base.BaseViewModel
import com.chat.database.addUserToFireStore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser

class RegisterViewModel : BaseViewModel<Navigator>() {
    var name = ObservableField<String>()
    var password = ObservableField<String>()
    var email = ObservableField<String>()
    var nameError = ObservableField<String>()
    var passwordError = ObservableField<String>()
    var emailError = ObservableField<String>()
    fun backLogin(){
        navigator?.backLogin()
    }

    val auth = Firebase.auth
    fun openHome(){
        if (validation()){
        createAccount()
        }
    }

    private fun createAccount() {
        showLoading.value=true
        auth.createUserWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener {task->
            showLoading.value=false
            when {
                task.isSuccessful -> {
                   // navigator?.openHome()
                   // Log.e("firebase", "successful"+task.exception?.localizedMessage)
                    // give uid about authountcahtion
                    createFireStoreUser(task.result.user?.uid)
                }
                else->{
                    messageLiveData.value=task.exception?.localizedMessage
                    Log.e("firebase","filed"+task.exception?.localizedMessage)
                }
            }
        }
    }

    private fun createFireStoreUser(uid: String?) {
       showLoading.value=true
        val appUser = ApplicationUser(
            id = uid,
            userName = name.get(),
            email = email.get()
        )
        addUserToFireStore(appUser ,
            OnSuccessListener {
                showLoading.value=false
            navigator?.openHome()
        }, OnFailureListener {
            showLoading.value=false
            messageLiveData.value=it.localizedMessage
        })
    }

    fun validation():Boolean{
        var valid = true
        if (name.get().isNullOrBlank()){
            nameError.set("please enter your name")
            valid=false
        }else{
            nameError.set(null)
        }
        if (email.get().isNullOrBlank()){
            emailError.set("please enter your email")
            valid=false
        }else{
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()){
            passwordError.set("please enter your password")
            valid=false
        }else{
            passwordError.set(null)
        }
        return valid
    }

}