package pa1pal.simpleimageloader.api

import androidx.lifecycle.LiveData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import pa1pal.simpleimageloader.models.ImageData
import pa1pal.simpleimageloader.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("wgkJgazE")
    fun getImageData(): LiveData<ApiResponse<List<ImageData>>>

    @GET
    fun getImage(@Url url: String, @Query("h") height: Int, @Query("w") width: Int): LiveData<ApiResponse<ResponseBody>>

    companion object {
        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl("https://pastebin.com/raw/")
                .client(OkHttpClient.Builder().apply {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }.build())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }
}