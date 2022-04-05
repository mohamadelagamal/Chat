package com.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.ui.R
import com.ui.databinding.ActivityRegisterBinding
import com.ui.home.HomeActivity
import com.ui.login.LoginActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>() , Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tarning = AnimationUtils.loadAnimation(this,R.anim.logintext)
        val image = findViewById(R.id.createAccountID) as TextView
        image.startAnimation(tarning)
        viewDataBinding.vmRegister=viewModel
        viewModel.navigator=this
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_register
    }

    override fun makeViewModelProvider(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun backLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun openHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


}