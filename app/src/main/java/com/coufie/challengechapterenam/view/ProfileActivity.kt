package com.coufie.challengechapterenam.view

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.ResponseUserUpdate
import com.coufie.challengechapterenam.datastore.UserManager
import com.coufie.challengechapterenam.network.FilmApi
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    lateinit var userManager: UserManager

     var id = ""
     var email = ""
     var password = ""
     var username = ""
     var fullname = ""
     var address = ""
     var dob = ""

    //kurang image simpan ke datastore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userManager = UserManager(this)
        userManager.userImage.asLiveData().observe(this){
            if (it !==""){
                Glide.with(this).load(it).into(iv_profile_image)
            }

        }
        initUM()
        logout()
        update()

        iv_profile_image.setOnClickListener {
            updateAvatar()
        }

    }


    fun logout(){
        btn_logout.setOnClickListener {
            GlobalScope.launch {
                userManager.clearData()

            }
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun initUM(){

        userManager.userId.asLiveData().observe(this, {
            this@ProfileActivity.id = it.toString()
//            Toast.makeText(this, "ini $id", Toast.LENGTH_SHORT).show()
        })

        userManager.userEmail.asLiveData().observe(this, {
            this@ProfileActivity.email = it.toString()
//            Toast.makeText(this, "ini $email", Toast.LENGTH_SHORT).show()
        })

        userManager.userPassword.asLiveData().observe(this, {
            this@ProfileActivity.password = it.toString()
        })

        userManager.userUsername.asLiveData().observe(this, {
            this@ProfileActivity.username = it.toString()
            et_update_username.setText(username)
        })

        userManager.userFullname.asLiveData().observe(this, {
            this@ProfileActivity.fullname = it.toString()
            if(fullname!!.length > 0 && fullname!!.isNotEmpty()){
                et_update_name.setText(fullname)
            }
        })

        userManager.userAddress.asLiveData().observe(this, {
            this@ProfileActivity.address = it.toString()
            if(address!!.length > 0 && address!!.isNotEmpty()){
                et_update_address.setText(address)
            }
        })

        userManager.userDob.asLiveData().observe(this, {
            this@ProfileActivity.dob = it.toString()
            if(dob!!.length > 0 && dob!!.isNotEmpty()){
                et_update_dob.setText(dob)
            }
        })

//        Toast.makeText(this, "ini $username", Toast.LENGTH_SHORT).show()

    }


    fun update(){
        btn_update_profile.setOnClickListener {

            if (et_update_address.text.isNotEmpty()
                && et_update_dob.text.isNotEmpty()
                && et_update_username.text.isNotEmpty()
                && et_update_name.text.isNotEmpty()){

                val address = et_update_address.text.toString()
                val dob = et_update_dob.text.toString()
                val username = et_update_username.text.toString()
                val fullname = et_update_name.text.toString()

                GlobalScope.launch {
                    userManager.saveData(id.toString(), email, password, username, fullname, address, dob)
                }
                Toast.makeText(this@ProfileActivity, "Update berhasil", Toast.LENGTH_LONG).show()
                postUserUpdate(id.toString(), address, dob, fullname, username)
            }else{
                Toast.makeText(this@ProfileActivity, "Data belum lengkap", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun postUserUpdate(id : String, address : String, dateofbirth : String,complete_name : String, username : String){
        FilmApi.instance.updateUser(id, address, dateofbirth, complete_name, username)
            .enqueue(object : retrofit2.Callback<ResponseUserUpdate>{
                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@ProfileActivity, "Update berhasil", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                    }else{
                        Toast.makeText(this@ProfileActivity, "Update berhasil", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    finish()
                }

            })

    }

    override fun onResume() {
        super.onResume()
        initUM()
    }

    override fun onDestroy() {
        super.onDestroy()
        initUM()

    }


    private fun updateAvatar() {
        val avatarIntent = Intent(Intent.ACTION_GET_CONTENT)
        avatarIntent.type = "image/*"
        if (avatarIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(avatarIntent, 2000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2000 && data != null){
            iv_profile_image.setImageURI(data?.data)
            //save image string ke datastore
            GlobalScope.launch {
                userManager.saveDataImage(data?.data.toString())
            }
        }
    }

}