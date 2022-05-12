package com.coufie.challengechapterenam.network

import com.coufie.challengechapterenam.model.GetAllFilmItem
import com.coufie.challengechapterenam.model.ResponseUserLogin
import com.coufie.challengechapterenam.model.ResponseUserRegister
import com.coufie.challengechapterenam.model.ResponseUserUpdate
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("film")
    fun getAllFilmNew() : Call<List<GetAllFilmItem>>

    @GET("apifilm.php")
    fun getAllFilm() : Call<List<GetAllFilmItem>>

    @POST("login.php")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ResponseUserLogin>

    @POST("register.php/")
    @FormUrlEncoded
    fun registerUser(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("username") username : String
    ) : Call<ResponseUserRegister>

    @POST("updateuser.php")
    @FormUrlEncoded
    fun updateUser(
        @Field("id") id : String,
        @Field("address") address : String,
        @Field("dateofbirth") dateofbirth : String,
        @Field("complete_name") complete_name : String,
        @Field("username") username : String

    ) : Call<ResponseUserUpdate>
}