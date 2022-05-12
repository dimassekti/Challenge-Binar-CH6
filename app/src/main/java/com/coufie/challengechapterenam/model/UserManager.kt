package com.coufie.challengechapterenam.model

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")

    companion object{
        val EMAILL = preferencesKey<String>("USER_EMAILL")
        val PASSWORD = preferencesKey<String>("USER_PASSWORD")


    }

    suspend fun saveData(email : String, password:String){
        dataStore.edit{
            it[EMAILL] = email
            it[PASSWORD] = password
        }
    }

    val userEmail : Flow<String> = dataStore.data.map{
        it[EMAILL] ?: ""
    }

    val userPassword : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

}