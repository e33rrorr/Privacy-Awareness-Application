package com.example.privacyawareness.data.network
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// 10.0.2.2 is how the Android emulator reaches "localhost" on my computer,
// where the FastAPI backend is running.
private const val BASE_URL = "http://10.0.2.2:8000/"

object RetrofitClient {
    val scanApi: ScanApi by lazy {

        val httpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            // Turns the JSON the backend sends back into Kotlin objects.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScanApi::class.java)
    }
}
