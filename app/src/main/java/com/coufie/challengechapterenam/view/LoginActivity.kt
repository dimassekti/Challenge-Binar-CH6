package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    var id = ""
    var email = ""
    var password = ""
    var username = ""
    var fullname = ""
    var address = ""
    var dob = ""


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
        tv_goto_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun login(){

        btn_login.setOnClickListener {
            email = et_input_email.text.toString()
            password = et_input_password.text.toString()

            if (email != "" || password != "" ){
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

                                id = dataUser[i].id

                                if(dataUser[i].email != null){
                                    this@LoginActivity.email = dataUser[i].email
                                }
                                if(dataUser[i].password != null){
                                    this@LoginActivity.password = dataUser[i].password
                                }
                                if(dataUser[i].username != null){
                                    this@LoginActivity.username = dataUser[i].username
                                }
                                if(dataUser[i].completeName != null){
                                    this@LoginActivity.fullname = dataUser[i].completeName
                                }
                                if(dataUser[i].address != null){
                                    this@LoginActivity.address = dataUser[i].address
                                }
                                if(dataUser[i].dateofbirth != null){
                                    this@LoginActivity.dob = dataUser[i].dateofbirth
                                }
                                GlobalScope.launch {

                                    userManager.saveData(id.toString(), email, password, username, fullname, address, dob)

                                }
//                                Toast.makeText(this@LoginActivity, "ini $id", Toast.LENGTH_SHORT).show()
                                Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            }
//                            Log.d("datauser", dataUser[i].email)

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