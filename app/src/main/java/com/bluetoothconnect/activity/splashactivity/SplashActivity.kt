package com.bluetoothconnect.activity.splashactivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.navigation.ui.AppBarConfiguration
import com.bluetoothconnect.R
import com.bluetoothconnect.activity.loginactivity.LoginActivity
import com.bluetoothconnect.activity.registrationnactivity.RegistrationActivity
import com.bluetoothconnect.activity.registrationnactivity.RegistrationNavigator
import com.bluetoothconnect.activity.registrationnactivity.RegistrationViewModel
import com.bluetoothconnect.databinding.ActivityLoginBinding
import com.bluetoothconnect.databinding.ActivitySplashBinding
import com.equalinfotech.miiwey.base.BaseActivity

class SplashActivity :  BaseActivity<ActivitySplashBinding, SplashViewModel>(),
    RegistrationNavigator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var email:String=""
    var password:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = ActivitySplashBinding.inflate(LayoutInflater.from(this))
        setContentView(dataBinding.root)
       // setSupportActionBar(binding.toolbar)

        loadSplashScreen()



    }
    private fun loadSplashScreen(){
        Handler().postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)



    }




    override fun onBackPressed() {

    }

    override fun onError(message: String) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModel(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }


}