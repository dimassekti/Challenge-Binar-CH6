package com.coufie.challengechapterenam.model.film_fav

import androidx.room.*

@Dao
interface FilmDao {

    @Insert
    fun insertNote(film: Film)  : Long

    @Query("SELECT * FROM Film")
    fun getAllFilm() : List<Film>

    @Delete
    fun deleteFilm(film: Film) : Int


}