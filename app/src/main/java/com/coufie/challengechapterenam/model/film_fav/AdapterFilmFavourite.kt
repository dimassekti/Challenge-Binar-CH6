package com.coufie.challengechapterenam.model.film_fav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coufie.challengechapterenam.R
import kotlinx.android.synthetic.main.item_film_fav.view.*

class AdapterFilmFavourite(val listFilmFavourite: List<Film>) : RecyclerView.Adapter<AdapterFilmFavourite.ViewHolder>() {

    var filmfavDb : FilmDatabase? = null

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterFilmFavourite.ViewHolder {

        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_film_fav, parent, false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AdapterFilmFavourite.ViewHolder, position: Int) {
        this.let {
            Glide.with(holder.itemView.context).load(listFilmFavourite!![position].image).into(holder.itemView.iv_filmimage)
        }
        holder.itemView.tv_filmdirector.text = listFilmFavourite[position].director.toString()
        holder.itemView.tv_filmtitle.text = listFilmFavourite[position].title.toString()
    }

    override fun getItemCount(): Int {
        return listFilmFavourite.size
    }


}