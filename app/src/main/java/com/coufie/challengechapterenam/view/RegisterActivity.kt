package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.ResponseUserRegister
import com.coufie.challengechapterenam.model.UserManager
import com.coufie.challengechapterenam.network.ApiService
import com.coufie.challengechapterenam.network.FilmApi
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

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

            register()

        }

    }

    fun register(){

        if(et_regusername.text.isEmpty() || et_regpassword.text.isEmpty()){
            Toast.makeText(this@RegisterActivity, "Data belum lengkap", Toast.LENGTH_SHORT).show()
        }else{
            val email = "default"
            val password = et_regpassword.text.toString()
            val username = et_regusername.text.toString()

            postUserRegister(email, password, username)
        }
    }


    fun postUserRegister(email : String, password : String, username : String){
        FilmApi.instance.registerUser(email, password, username)
            .enqueue(object : retrofit2.Callback<ResponseUserRegister>{
                override fun onResponse(
                    call: Call<ResponseUserRegister>,
                    response: Response<ResponseUserRegister>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@RegisterActivity, "Register Berhasil", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }else{
                        Toast.makeText(this@RegisterActivity, "Register Gagal", Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<ResponseUserRegister>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Register Gagal", Toast.LENGTH_LONG).show()
                }

            })
    }
}