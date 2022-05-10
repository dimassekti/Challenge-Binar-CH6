package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.UserManager

class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)

        Handler().postDelayed({
//            if(userManager.userUsername.toString() != "null"){
//                startActivity(Intent(this, HomeAct::class.java))
//            }else{
//                startActivity(Intent(this, LoginAct::class.java))
//            }

            startActivity(Intent(this, LoginActivity::class.java))

        }, 3000)
    }
}