package com.hsofiamunoz.moviedbwebservice.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hsofiamunoz.moviedbwebservice.R
import com.hsofiamunoz.moviedbwebservice.databinding.MovieListItemBinding
import com.hsofiamunoz.moviedbwebservice.model.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter (
    private val onItemClicked:(Movie)-> Unit,
        ): RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    private var listMovies:MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        holder.bind(listMovies[position])
        holder.itemView.setOnClickListener { onItemClicked(listMovies[position]) }
    }

    override fun getItemCount(): Int =  listMovies.size

    fun appendItems(newItems: MutableList<Movie>){
        listMovies.clear()
        listMovies.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = MovieListItemBinding.bind(view)
        private val context: Context = binding.root.context


        fun bind(movie:Movie){
            with(binding){
                titleTextView.text = movie.title
                releaseTextView.text =context.getString(R.string.release_info, movie.releaseDate)
                voteTextView.text = context.getString(R.string.vote_avg_info, movie.voteAverage.toString())

                if(movie.posterPath!=null)
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.posterPath).into(pictureImageView)
            }

        }
    }
}