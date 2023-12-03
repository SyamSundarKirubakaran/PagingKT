package work.syam.pagingkt.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import work.syam.pagingkt.R
import work.syam.pagingkt.databinding.LoadStateItemBinding

class MoviesLoadStateAdapter(
    private val mRetryCallback: View.OnClickListener
) :
    LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        // Return new LoadStateViewHolder object
        return LoadStateViewHolder(parent, mRetryCallback)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        // Call Bind Method to bind visibility  of views
        holder.bind(loadState)
    }

    class LoadStateViewHolder internal constructor(
        parent: ViewGroup,
        retryCallback: View.OnClickListener
    ) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false)
        ) {
        // Define Progress bar
        private val mProgressBar: ProgressBar

        // Define error TextView
        private val mErrorMsg: TextView

        // Define Retry button
        private val mRetry: Button

        init {
            val binding: LoadStateItemBinding = LoadStateItemBinding.bind(itemView)
            mProgressBar = binding.progressBar
            mErrorMsg = binding.errorMsg
            mRetry = binding.retryButton
            mRetry.setOnClickListener(retryCallback)
        }

        fun bind(loadState: LoadState?) {
            // Check load state
            if (loadState is LoadState.Error) {
                // Get the error
                val loadStateError = loadState as LoadState.Error
                // Set text of Error message
                mErrorMsg.text = loadStateError.error.localizedMessage
            }
            // set visibility of widgets based on LoadState
            mProgressBar.visibility =
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            mRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            mErrorMsg.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        }
    }
}