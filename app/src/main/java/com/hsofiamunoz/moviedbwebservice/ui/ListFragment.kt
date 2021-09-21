package com.hsofiamunoz.moviedbwebservice.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hsofiamunoz.moviedbwebservice.R
import com.hsofiamunoz.moviedbwebservice.databinding.FragmentListBinding
import com.hsofiamunoz.moviedbwebservice.model.Movie
import com.hsofiamunoz.moviedbwebservice.model.MoviesList
import com.hsofiamunoz.moviedbwebservice.server.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment() {

    private  var _binding: FragmentListBinding?= null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root :View = binding.root

        // Codigo
        moviesAdapter = MoviesAdapter(onItemClicked = {onMovieItemClicked(it)})

        binding.moviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = moviesAdapter
            setHasFixedSize(false)
        }

        loadMovies()

        return root

    }

    private fun loadMovies() {
        val apikey = "ccd6f63380a85514cda498b152645fad"
        ApiService.create()
            .getTopRated(apikey)
            .enqueue(object: Callback<MoviesList>{
                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    Log.d("Error",t.message.toString())
                }

                override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
                    if (response.isSuccessful){
                        var listMovies : MutableList<Movie> = response.body()?.movies as MutableList<Movie>
                        moviesAdapter.appendItems(listMovies)
                    }
                }
            })
    }

    private fun onMovieItemClicked(movie: Movie) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(movie = movie))
    }


}