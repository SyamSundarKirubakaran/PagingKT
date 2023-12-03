package work.syam.pagingkt.network

import work.syam.pagingkt.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * This class will provide us a single instance of Retrofit
 */
object APIClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // Define APIInterface
    private var apiRequests: APIRequests? = null
    val instance: APIRequests
        // Method to get APIInterface
        get() {
            // Check for null
            if (apiRequests == null) {

                // Optional - Setup Http logging for debug purpose
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                // Create OkHttp Client
                val client = OkHttpClient.Builder()
                // Set logging
                client.addInterceptor(interceptor)
                // Add request interceptor to add API key as query string parameter to each request
                client.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url
                    val url =
                        originalHttpUrl.newBuilder() // Add API Key as query string parameter
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .build()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .url(url)
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })

                // Create retrofit instance
                val retrofit = Retrofit.Builder() // set base url
                    .baseUrl(BASE_URL)
                    .client(client.build()) // Add Gson converter
                    .addConverterFactory(GsonConverterFactory.create()) // Add RxJava support for Retrofit
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()

                // Init APIInterface
                apiRequests = retrofit.create(APIRequests::class.java)
            }
            return apiRequests!!
        }
}