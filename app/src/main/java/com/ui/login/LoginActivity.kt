package com.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.ui.R
import com.ui.databinding.ActivityLoginBinding
import com.ui.register.RegisterActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() , Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val tarning = AnimationUtils.loadAnimation(this,R.anim.logintext)
//        val image = findViewById(R.id.loginID) as TextView
//        image.startAnimation(tarning)
        viewDataBinding.vmLogin=viewModel
        viewModel.navigator = this
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_login
    }

    override fun makeViewModelProvider(): LoginViewModel {
       return ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun openRegister() {
//        val intent = Intent(this,RegisterActivity::class.java)
//        startActivity(intent)
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

    override fun openHome() {
//        val intent = Intent(this,HomeActivity::class.java)
//        startActivity(intent)
//        finish()
    }
}