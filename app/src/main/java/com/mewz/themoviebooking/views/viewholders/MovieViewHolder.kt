package com.mewz.themoviebooking.views.viewholders

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.databinding.ViewholderMovieBinding
import com.mewz.themoviebooking.delegates.MovieViewHolderDelegate
import com.mewz.themoviebooking.network.IMAGE_BASE_URL

class MovieViewHolder(itemView: View, private var delegate: MovieViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderMovieBinding

    private var mMovie: MovieVO? = null

    init {
        binding = ViewholderMovieBinding.bind(itemView)

        itemView.setOnClickListener {
            mMovie?.let { movieId ->
                movieId.id?.let { delegate.onTapMovie(it) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindData(movie: MovieVO) {
        mMovie = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivMoviePoster)

        binding.tvMovieName.text = movie.originalTitle
        binding.tvReleaseDate.text = movie.changeReleaseDateFormat("home")

    }
}