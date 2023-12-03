package work.syam.pagingkt.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIRequests {
    // Define Get request with query string parameter as page number
    @GET("movie/popular")
    fun getMoviesByPage(@Query("page") page: Int): Single<MovieResponse>
}
