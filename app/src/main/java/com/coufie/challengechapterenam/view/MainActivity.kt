package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.UserManager


//berisi splashscreen
class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
//            if(userManager.userUsername != null){
//                startActivity(Intent(this, HomeActivity::class.java))
//            }else{
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
            startActivity(Intent(this, LoginActivity::class.java))


        }, 3000)
    }
}