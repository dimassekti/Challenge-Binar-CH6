package com.coufie.challengechapterenam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coufie.challengechapterenam.model.GetAllUserItem
import com.coufie.challengechapterenam.model.ResponseUserUpdate
import com.coufie.challengechapterenam.network.FilmApi
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel(){

    lateinit var userLiveData: MutableLiveData<List<GetAllUserItem>>
    lateinit var userUpdateLiveData: MutableLiveData<ResponseUserUpdate>

    init {
        userLiveData = MutableLiveData()
    }

    fun getUserLiveDataObserver() : MutableLiveData<List<GetAllUserItem>>{
        return userLiveData
    }

    fun makeApiUser(){
        FilmApi.instance.getAllUser()
            .enqueue(object  : retrofit2.Callback<List<GetAllUserItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserItem>>,
                    response: Response<List<GetAllUserItem>>
                ) {
                    if(response.isSuccessful){
                        userLiveData.postValue(response.body())
                    }else{
                        userLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserItem>>, t: Throwable) {
                    userLiveData.postValue(null)
                }


            })
    }

    fun postUserUpdate(id : String, address : String, dateofbirth : String,complete_name : String, username : String){
        FilmApi.instance.updateUser(id, address, dateofbirth, complete_name, username)
            .enqueue(object : retrofit2.Callback<ResponseUserUpdate>{
                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {
                    if(response.isSuccessful){
                        userUpdateLiveData.postValue(response.body())
                    }else{
                        userUpdateLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    userUpdateLiveData.postValue(null)
                }

            })

    }
}