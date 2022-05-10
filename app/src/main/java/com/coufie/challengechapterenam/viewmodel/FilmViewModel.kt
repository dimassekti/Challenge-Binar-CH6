package com.coufie.challengechapterenam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coufie.challengechapterenam.model.GetAllFilmItem
import com.coufie.challengechapterenam.network.FilmApi
import retrofit2.Call
import retrofit2.Response

class FilmViewModel : ViewModel(){


    lateinit var liveDataFilm : MutableLiveData<List<GetAllFilmItem>>

    init{
        liveDataFilm = MutableLiveData()
    }

    fun getLiveDataFilmm() : MutableLiveData<List<GetAllFilmItem>> {
        return liveDataFilm
    }

    fun getDataFilm(){
        FilmApi.instance.getAllFilm()
            .enqueue(object : retrofit2.Callback<List<GetAllFilmItem>>{
                override fun onResponse(
                    call: Call<List<GetAllFilmItem>>,
                    response: Response<List<GetAllFilmItem>>
                ) {
                    if(response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    }else{
                        liveDataFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetAllFilmItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }

}