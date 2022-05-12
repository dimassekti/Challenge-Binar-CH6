package com.coufie.challengechapterenam.view.detail_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.GetAllFilmItem
import com.coufie.challengechapterenam.model.film_fav.Film
import com.coufie.challengechapterenam.model.film_fav.FilmDatabase
import com.coufie.challengechapterenam.view.FavouriteActivity
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DetailFilm : AppCompatActivity() {

    var filmfavdb : FilmDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        filmfavdb = FilmDatabase.getInstance(this)

        detailfilm()

        btn_addfavfilm.setOnClickListener{
            addfavfilm()
        }
    }

    fun detailfilm(){
        val detailFilm = intent.getParcelableExtra<GetAllFilmItem>("DETAILFILMM")

        tv_film_director.text = detailFilm!!.director
        tv_film_release_date.text = detailFilm!!.createdAt
        tv_film_synopsis.text = detailFilm!!.synopsis
        tv_film_title.text = detailFilm!!.title
        Glide.with(this).load(detailFilm!!.image).into(iv_film_cover)
    }

    fun addfavfilm(){

        val detailFilm = intent.getParcelableExtra<GetAllFilmItem>("DETAILFILM")

        GlobalScope.async {
            val director = detailFilm!!.director
            val releasedate = detailFilm!!.releaseDate
            val synopsis = detailFilm!!.synopsis
            val title = detailFilm!!.title
            val image = detailFilm!!.image

            filmfavdb?.filmDao()?.insertNote(Film(null, director, image, releasedate, synopsis, title) )

        }
        startActivity(Intent(this, FavouriteActivity::class.java))
    }
}