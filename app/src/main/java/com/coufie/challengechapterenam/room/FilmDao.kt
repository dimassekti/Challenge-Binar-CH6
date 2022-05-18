package com.coufie.challengechapterenam.room

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