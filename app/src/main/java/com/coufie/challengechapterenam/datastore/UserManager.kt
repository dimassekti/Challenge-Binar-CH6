package com.coufie.challengechapterenam.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")
    private val imageData: DataStore<Preferences> = context.createDataStore(name = "image_prefs")

    companion object{
        val ID = preferencesKey<String>("USER_ID")
        val EMAILL = preferencesKey<String>("USER_EMAILL")
        val PASSWORD = preferencesKey<String>("USER_PASSWORD")
        val USERNAME = preferencesKey<String>("USER_USERNAME")
        val FULLNAME = preferencesKey<String>("USER_FULLNAME")
        val ADDRESS = preferencesKey<String>("USER_ADDRESS")
        val DOB = preferencesKey<String>("USER_DOB")
        val IMAGE  = preferencesKey<String>("USER_IMAGE")

    }

    suspend fun saveData(id: String, email : String, password:String, username:String, fullname:String, address:String, dob:String){
        dataStore.edit{
            it[ID] = id
            it[EMAILL] = email
            it[PASSWORD] = password
            it[USERNAME] = username
            it[FULLNAME] = fullname
            it[ADDRESS] = address
            it[DOB] = dob

        }
    }

    suspend fun saveDataImage(image: String) {
        imageData.edit {
            it[IMAGE] = image

        }
    }

    suspend fun deleteDataImage(){
        imageData.edit{
            it.clear()

        }
    }

    val userId : Flow<String> = dataStore.data.map {
        it[ID] ?: ""
    }

    val userEmail : Flow<String> = dataStore.data.map{
        it[EMAILL] ?: ""
    }

    val userPassword : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val userUsername : Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val userFullname : Flow<String> = dataStore.data.map {
        it[FULLNAME] ?: ""
    }

    val userAddress : Flow<String> = dataStore.data.map {
        it[ADDRESS] ?: ""
    }

    val userDob : Flow<String> = dataStore.data.map {
        it[DOB] ?: ""
    }

    val userImage : kotlinx.coroutines.flow.Flow<String> = imageData.data.map {
        it [IMAGE] ?: ""
    }

    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

}