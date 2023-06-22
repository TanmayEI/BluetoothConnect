package com.bluetoothconnect.activity.loginactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.bluetoothconnect.MainActivity
import com.bluetoothconnect.R
import com.bluetoothconnect.activity.registrationnactivity.RegistrationActivity
import com.bluetoothconnect.activity.registrationnactivity.RegistrationNavigator
import com.bluetoothconnect.activity.registrationnactivity.RegistrationViewModel
import com.bluetoothconnect.activity.splashactivity.SplashViewModel
import com.bluetoothconnect.databinding.ActivityLoginBinding
import com.bluetoothconnect.databinding.ActivityRegistrationBinding
import com.equalinfotech.miiwey.base.BaseActivity

class LoginActivity :  BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    RegistrationNavigator {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(dataBinding.root)
       // setSupportActionBar(binding.toolbar)


        dataBinding.register.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        dataBinding.login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }


    override fun onBackPressed() {

    }

    override fun onError(message: String) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }


}