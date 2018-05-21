package io.github.ziginsider.epam_laba14.retrofit

import io.github.ziginsider.epam_laba14.model.RecentPhotos
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Declaration a Retrofit interface for getting recent photos from flickr
 *
 * @since 2018-05-10
 * @author Alex Kisel
 */
interface RetrofitService {

    @GET("/services/rest")
    fun recentPhotos(@Query("method") method: String,
                     @Query("api_key") key: String,
                     @Query("format") format: String,
                     @Query("per_page") count: Int,
                     @Query("page") page: Int,
                     @Query("nojsoncallback") raw: Int,
                     @Query("extras") extras: String): Observable<RecentPhotos>

    companion object {
        fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.flickr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }

}
