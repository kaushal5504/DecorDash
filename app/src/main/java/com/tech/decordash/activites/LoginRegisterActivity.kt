package com.tech.decordash.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tech.decordash.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
    }
}