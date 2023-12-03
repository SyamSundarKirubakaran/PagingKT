package work.syam.pagingkt.view

import androidx.lifecycle.ViewModel

import androidx.lifecycle.*

import androidx.paging.Pager

import androidx.paging.PagingConfig

import androidx.paging.PagingData

import androidx.paging.rxjava3.*


import io.reactivex.rxjava3.core.Flowable

import work.syam.pagingkt.network.Movie


class MovieViewModel : ViewModel() {
    // Define Flowable for movies
    lateinit var pagingDataFlow: Flowable<PagingData<Movie>>

    init {
        init()
    }

    // Init ViewModel Data
    private fun init() {

        // Define Paging Source
        val moviePagingSource = MoviePagingSource()

        // Create new pager
        val pager: Pager<Int, Movie> = Pager<Int, Movie>( // Create new paging config
            PagingConfig(
                20,  // pageSize - Count of items in one page
                20,  // prefetchDistance - Number of items to prefetch
                false,  // enablePlaceholders - Enable placeholders for data which is not yet loaded
                20,  // initialLoadSize - Count of items to be loaded initially
                20 * 499 // maxSize - Count of total items to be shown in recyclerview
            )
        )
        { moviePagingSource } // set paging source

        // inti Flowable
        pagingDataFlow = pager.flowable.cachedIn(viewModelScope)
    }
}
