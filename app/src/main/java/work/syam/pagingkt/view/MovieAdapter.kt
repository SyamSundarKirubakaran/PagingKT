package work.syam.pagingkt.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import work.syam.pagingkt.databinding.MovieRowBinding
import work.syam.pagingkt.network.Movie
import kotlin.Int

class MoviesAdapter(diffCallback: DiffUtil.ItemCallback<Movie>) :
    PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Return MovieViewHolder
        return MovieViewHolder(
            MovieRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Get current movie
        val currentMovie: Movie? = getItem(position)
        // Check for null
        if (currentMovie != null) {
            // Set Image of Movie using Picasso Library
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + currentMovie.posterPath)
                .fit()
                .into(holder.binding.imageViewMovie)

            // Set rating of movie
            holder.binding.textViewRating.text = "${currentMovie.voteAverage}"
        }
    }

    override fun getItemViewType(position: Int): Int {
        // set ViewType
        return if (position == itemCount) MOVIE_ITEM else LOADING_ITEM
    }

    class MovieViewHolder(binding: MovieRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // Define movie_item layout view binding
        var binding: MovieRowBinding

        init {
            // init binding
            this.binding = binding
        }
    }

    companion object {
        // Define Loading ViewType
        const val LOADING_ITEM = 0

        // Define Movie ViewType
        const val MOVIE_ITEM = 1
    }
}