package com.coufie.challengechapterenam.network

import com.coufie.challengechapterenam.model.GetAllFilmItem
import com.coufie.challengechapterenam.model.ResponseUserLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("apifilm.php")
    fun getAllFilm() : Call<List<GetAllFilmItem>>

    @POST("login.php")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ResponseUserLogin>
}