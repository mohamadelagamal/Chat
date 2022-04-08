package com.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import com.DataUtil
import com.base.BaseViewModel
import com.chat.database.getUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser

class LoginViewModel : BaseViewModel<Navigator>() {
     var email = ObservableField<String>()
     var password = ObservableField<String>()
     val emailError = ObservableField<String>()
     val passwordError = ObservableField<String>()
     fun openRegister(){
          navigator?.openRegister()
     }
    var auth = Firebase.auth
    fun openHome(){
        if (validation()){
            loginAccountFormFirebase()
        }
    }

    private fun loginAccountFormFirebase() {
        showLoading.value=true
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener { task->
            showLoading.value=false
            when{
                task.isSuccessful->{
                  //  navigator?.openHome()
                    checkUserFormFireStore(task.result.user?.uid)
                    Log.e("firebase","Success Login"+task.exception?.localizedMessage)
                }else->{
                    messageLiveData.value="password or email is wrong"
                }
            }
        }
    }

    private fun checkUserFormFireStore(uid: String?) {
    showLoading.value=true
        getUser(uid!!, OnSuccessListener {
            showLoading.value=false
            // transfer data form AppUser to make compress in user variable
            val user = it.toObject(ApplicationUser::class.java)
                if (user!=null){
                   DataUtil.user=user
                    navigator?.openHome()
                    return@OnSuccessListener
                }
            messageLiveData.value="Invalid email or password"
        } , OnFailureListener {
            showLoading.value=false
            messageLiveData.value="Invalid email or password"
        })
    }

    fun validation():Boolean{
        var valid = true
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