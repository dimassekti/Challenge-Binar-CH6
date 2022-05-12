package com.coufie.challengechapterenam.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coufie.challengechapterenam.R
import com.coufie.challengechapterenam.model.UserManager
import com.coufie.challengechapterenam.view.adapter.FilmAdapter
import com.coufie.challengechapterenam.view.detail_page.DetailFilm
import com.coufie.challengechapterenam.viewmodel.FilmViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_film.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    lateinit var filmAdapter: FilmAdapter
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userManager = UserManager(this)

        initRecycler()
        logout()

    }

    fun initRecycler(){
        filmAdapter = FilmAdapter(){
            val pindah = Intent(this, DetailFilm::class.java)
            pindah.putExtra("DETAILFILM", it)
            startActivity(pindah)
        }
        rv_film.layoutManager = LinearLayoutManager(this)
        rv_film.adapter = filmAdapter
        initFilmViewModel()
    }

    fun logout(){
        btn_logout.setOnClickListener {
            GlobalScope.launch {
                userManager.clearData()

            }
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
    }

    fun initFilmViewModel(){
        val viewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        viewModel.getLiveDataFilmm().observe(this, Observer {
            if(it != null){
                filmAdapter.setFilmList(it)
                filmAdapter.notifyDataSetChanged()
            }else{

            }
        })
        viewModel.getDataFilm()
    }



}