package com.coufie.challengechapterenam.view.detail_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.GetAllFilmItem
import com.coufie.challengechapterenam.room.Film
import com.coufie.challengechapterenam.room.FilmDatabase
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

        iv_addfavfilm.setOnClickListener{

            GlobalScope.async {

                val detailFilm = intent.getParcelableExtra<GetAllFilmItem>("DETAILFILMM")

                val director = detailFilm!!.director
                val releasedate = detailFilm!!.releaseDate
                val synopsis = detailFilm!!.synopsis
                val title = detailFilm!!.title
                val image = detailFilm!!.image
                val id = detailFilm!!.id

                runOnUiThread {
                    var checkdata = filmfavdb?.filmDao()?.insertNote(Film(id.toInt(), director, image, releasedate, synopsis, title) )
                    if(checkdata != 0.toLong()){
                        Toast.makeText(this@DetailFilm, "Film Ditambahkan Ke Favourite", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@DetailFilm, "gagal", Toast.LENGTH_SHORT).show()
                    }

                    Log.d("tes2", checkdata.toString())
                }

            }
        }
    }

    fun detailfilm(){
        val detailFilm = intent.getParcelableExtra<GetAllFilmItem>("DETAILFILMM")

        tv_film_director.text = "Director : ${detailFilm!!.director}"
        tv_film_release_date.text = "Release date : ${detailFilm!!.releaseDate}"
        tv_film_synopsis.text = detailFilm!!.synopsis
        tv_film_title.text = detailFilm!!.title
        Glide.with(this).load(detailFilm!!.image).into(iv_film_cover)
    }

    suspend fun addfavfilm(){

//        startActivity(Intent(this, FavouriteActivity::class.java))
    }
}