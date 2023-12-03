package work.syam.pagingkt.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import work.syam.pagingkt.databinding.ActivityMainBinding
import work.syam.pagingkt.utils.GridSpace
import work.syam.pagingkt.utils.MovieComparator

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create View binding object
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create new MoviesAdapter object and provide
        val moviesAdapter = MoviesAdapter(MovieComparator())

        // Create ViewModel
        val movieViewModel: MovieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        // Subscribe to to paging data
        val disposable: Disposable = movieViewModel.pagingDataFlow.subscribe { moviePagingData ->
            // submit new data to recyclerview adapter
            moviesAdapter.submitData(lifecycle, moviePagingData)
        }
        // Adding an Observable to the disposable
        compositeDisposable.add(disposable)

        // Create GridlayoutManger with span of count of 2
        val gridLayoutManager = GridLayoutManager(this, 2)

        // Finally set LayoutManger to recyclerview
        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager)

        // Add ItemDecoration to add space between recyclerview items
        binding.recyclerViewMovies.addItemDecoration(GridSpace(2, 12, true))

        // set adapter
        binding.recyclerViewMovies.setAdapter( // concat movies adapter with header and footer loading view
            // This will show end user a progress bar while pages are being requested from server
            moviesAdapter.withLoadStateFooter( // Pass footer load state adapter.
                // When we will scroll down and next page request will be sent
                // while we get response form server Progress bar will show to end user
                // If request success Progress bar will hide and next page of movies
                // will be shown to end user or if request will fail error message and
                // retry button will be shown to resend the request
                MoviesLoadStateAdapter { view -> moviesAdapter.retry() }
            )
        )

        // set Grid span
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // If progress will be shown then span size will be 1 otherwise it will be 2
                return if (moviesAdapter.getItemViewType(position) === MoviesAdapter.LOADING_ITEM) 1 else 2
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Using dispose will clear all and set isDisposed = true,
        // so it will not accept any new disposable
        compositeDisposable.dispose()
    }
}