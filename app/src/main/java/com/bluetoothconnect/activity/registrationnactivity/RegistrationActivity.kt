package com.bluetoothconnect.activity.registrationnactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.ui.AppBarConfiguration
import com.bluetoothconnect.R
import com.bluetoothconnect.activity.loginactivity.LoginActivity
import com.bluetoothconnect.databinding.ActivityRegistrationBinding
import com.equalinfotech.miiwey.base.BaseActivity

class RegistrationActivity : BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>(),
    RegistrationNavigator {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getViewModel(): Class<RegistrationViewModel> {
        return RegistrationViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = ActivityRegistrationBinding.inflate(LayoutInflater.from(this))
        setContentView(dataBinding.root)
       // setSupportActionBar(binding.toolbar)

        dataBinding.loginNow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onError(message: String) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_registration
    }


/*
    private fun validation() {
        email = binding.loginEmail.text.toString().trim()
        password = binding.loginPassword.text.toString().trim()
        if (email.isEmpty()) {
            binding.loginEmail.requestFocus()
            binding.loginEmail.setError("Enter Email")
        } else if (!CommonUtils.isEmailValid(email)) {
            binding.loginEmail.requestFocus()
            binding.loginEmail.setError("Enter valid Email")
        } else if (password.isEmpty()) {
            binding.loginPassword.requestFocus()
            binding.loginPassword.setError("Enter Password")
        }
        */
/* else if (password.length < 6) {
             login_password.requestFocus()
             login_password.setError("Enter Password more than 6")
         }*//*

        else {
            if (Utils.isNetworkAvailable(this)) {
               // login()
            } else {
                Snackbar.make(binding.root, "Please check your Internet Connection", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
*/


}