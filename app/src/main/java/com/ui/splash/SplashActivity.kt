package com.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.DataUtil
import com.chat.database.getUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.ui.R
import com.ui.home.HomeActivity
import com.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val tarning = AnimationUtils.loadAnimation(this,R.anim.traning)
        val image = findViewById(R.id.logo) as ImageView
        image.startAnimation(tarning)
        Handler(Looper.getMainLooper()).postDelayed({
           checkLoginInFirebase()
        },2000)
    }

    private fun checkLoginInFirebase() {
        val firebaseUser = Firebase.auth.currentUser
       when{
           firebaseUser==null->{
               openLoginAccount()
           }
           else->{
               getUser(firebaseUser.uid, OnSuccessListener {
                val user = it.toObject(ApplicationUser::class.java)
                DataUtil.user=user
                openHome()
               }, OnFailureListener {
                   openLoginAccount()
               })
           }
       }
    }

    private fun openHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openLoginAccount() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}