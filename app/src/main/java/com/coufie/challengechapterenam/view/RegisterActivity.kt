package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.UserManager
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        userManager = UserManager(this)

        btn_register.setOnClickListener {
            val username = et_regusername.text.toString()
            val password = et_regpassword.text.toString()


//            GlobalScope.launch {
//                userManager.saveData(username, password)
//            }

            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}