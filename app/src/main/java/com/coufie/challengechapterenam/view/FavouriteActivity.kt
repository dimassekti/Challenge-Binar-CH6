package com.coufie.challengechapterenam.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.film_fav.AdapterFilmFavourite
import com.coufie.challengechapterenam.model.film_fav.FilmDatabase
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {

    var filmDb : FilmDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)


        filmDb = FilmDatabase.getInstance(this)
        getFilmFav()

    }

    fun getFilmFav(){

        rv_favfilm.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            val listFavFilm = filmDb?.filmDao()?.getAllFilm()
            runOnUiThread{
                if(listFavFilm?.size!! < 1){
                    tv.setText("Favourite-ku masing kosong")
                }else{
                    listFavFilm.let {
                        rv_favfilm.adapter = AdapterFilmFavourite(it!!)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getFilmFav()
    }

    override fun onDestroy() {
        super.onDestroy()
        getFilmFav()
    }

}