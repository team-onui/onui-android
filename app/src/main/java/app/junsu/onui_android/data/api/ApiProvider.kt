package app.junsu.onui_android.data.api

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiProvider {
    private var BASE_URL = "https://onui.kanghyuk.co.kr/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
    }


    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(getLoggingInterceptor())
                    .addInterceptor(getTokenInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getTokenInterceptor(): Interceptor {
        return Interceptor { chain ->
            val token = sharedPreferences.getString("token", "") ?: ""
            val request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer $token"
                )
                .build()
            chain.proceed(request)
        }
    }

    fun diaryApi(): DiaryApi = getRetrofit().create(DiaryApi::class.java)

    fun timelineApi(): TimelineApi = getRetrofit().create(TimelineApi::class.java)
    fun loginApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(getLoggingInterceptor())
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AuthApi::class.java)
    }

    fun userApi(): UserApi = getRetrofit().create(UserApi::class.java)

    fun missionApi(): MissionApi = getRetrofit().create(MissionApi::class.java)

    fun analysisApi(): AnalysisApi = getRetrofit().create(AnalysisApi::class.java)
}