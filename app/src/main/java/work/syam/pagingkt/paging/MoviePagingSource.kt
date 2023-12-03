package work.syam.pagingkt.view

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import work.syam.pagingkt.network.APIClient
import work.syam.pagingkt.network.MovieResponse
import work.syam.pagingkt.network.Movie


class MoviePagingSource : RxPagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        val anchorPosition = state.anchorPosition ?: return null
        val (_, prevKey, nextKey) = state.closestPageToPosition(anchorPosition)
            ?: return null
        if (prevKey != null) {
            return prevKey + 1
        }
        return if (nextKey != null) {
            nextKey - 1
        } else null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {

        // If page number is already there then init page variable with it otherwise we are loading fist page
        val page = if (params.key != null) params.key!! else 1
        // Send request to server with page number
        return APIClient.instance
            .getMoviesByPage(page) // Subscribe the result
            .subscribeOn(Schedulers.io()) // Map result top List of movies
            .map(MovieResponse::getResults) // Map result to LoadResult Object
            .map { movies -> toLoadResult(movies, page) } // when error is there return error
            .onErrorReturn { throwable: Throwable -> LoadResult.Error(throwable) }
    }

    // Method to map Movies to LoadResult object
    private fun toLoadResult(movies: List<Movie>, page: Int): LoadResult<Int, Movie> {
        return LoadResult.Page(movies, if (page == 1) null else page - 1, page + 1)
    }
}
