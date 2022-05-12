package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.GetAllUserItem
import com.coufie.challengechapterenam.model.ResponseUserLogin
import com.coufie.challengechapterenam.model.UserManager
import com.coufie.challengechapterenam.network.FilmApi
import com.coufie.challengechapterenam.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var un = false
    var pw = false
    var email = ""
    var password = ""
    lateinit var dataUser : List<GetAllUserItem>
    lateinit var viewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        userManager = UserManager(this)

        getUserData()
        register()
        login()

    }

    fun register(){
        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun login(){

        btn_login.setOnClickListener {
            email = et_email.text.toString()
            password = et_password.text.toString()

            if (email != "" || password != "" ){
                GlobalScope.launch {
                    userManager.saveData(email, password)
                }

                postUserLogin(email, password)
            }

        }
    }

    fun postUserLogin(email:String, password:String){
        FilmApi.instance.loginUser(email, password)
            .enqueue(object  : retrofit2.Callback<ResponseUserLogin>{
                override fun onResponse(
                    call: Call<ResponseUserLogin>,
                    response: Response<ResponseUserLogin>
                ) {
                    if(response.isSuccessful){
                        for(i in dataUser.indices){
                            if(email == dataUser[i].email && password == dataUser[i].password ){

                                Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            }
                        }
                    }else{
                        Toast.makeText(this@LoginActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
//                    Toast.makeText(this@LoginActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getUserData(){
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.getUserLiveDataObserver().observe(this, Observer {
            dataUser = it
        })
        viewModel.makeApiUser()
    }


    override fun onResume() {
        super.onResume()
        getUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}