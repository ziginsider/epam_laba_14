package io.github.ziginsider.epam_laba14.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Declaration a Retrofit interface for image downloading
 * of download
 *
 * @since 2018-04-10
 * @author Alex Kisel
 */
interface RetrofitService {
    @GET
    fun downloadImage(@Url url: String): Call<ResponseBody>
}