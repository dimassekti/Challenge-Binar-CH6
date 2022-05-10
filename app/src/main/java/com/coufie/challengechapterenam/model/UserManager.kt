package com.coufie.challengechapterenam.model

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")

    companion object{
        val USERNAME = preferencesKey<String>("USER_USERNAME")
        val PASSWORD = preferencesKey<String>("USER_PASSWORD")

    }

    suspend fun saveData(nama : String, password:String){
        dataStore.edit{
            it[USERNAME] = nama
            it[PASSWORD] = password
        }
    }

    val userUsername : Flow<String> = dataStore.data.map{
        it[USERNAME] ?: "null"
    }

    val userPassword : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: "null"
    }

    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

}