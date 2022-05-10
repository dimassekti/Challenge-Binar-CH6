package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.ResponseUserLogin
import com.coufie.challengechapterenam.model.UserManager
import com.coufie.challengechapterenam.network.FilmApi
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var un = false
    var pw = false
    var username = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        userManager = UserManager(this)

        btn_login.setOnClickListener {
            username = et_username.text.toString()
            password = et_password.text.toString()

            userManager.userUsername.asLiveData().observe(this,{
                if(username==it.toString()){
                    un = true
                }
            })
            userManager.userPassword.asLiveData().observe(this,{
                if(password==it.toString()){
                    pw = true
                }
            })

//            if(un && pw){
//                startActivity(Intent(this, HomeActivity::class.java))
//            }else{
//                tv_error.setText("Akun tidak ditemukan")
////                  startActivity(Intent(this@LoginAct, HomeAct::class.java))
//            }

            GlobalScope.launch {
                userManager.saveData(username, password)
            }

            postUserLogin(username, password)
        }
    }

    fun postUserLogin(username:String, password:String){
        FilmApi.instance.loginUser(username, password)
            .enqueue(object  : retrofit2.Callback<ResponseUserLogin>{
                override fun onResponse(
                    call: Call<ResponseUserLogin>,
                    response: Response<ResponseUserLogin>
                ) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }

                override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Data tidak ditemukan", Toast.LENGTH_LONG).show()
                }

            })
    }


}