package com.coufie.challengechapterenam.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUserLogin (

    @SerializedName("email")
    val username: String,
    @SerializedName("password")
    val password: String

) : Parcelable