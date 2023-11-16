package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.request.RemindRequest
import app.junsu.onui_android.data.response.diary.DayDiaryResponse
import app.junsu.onui_android.data.response.diary.ImageResponse
import app.junsu.onui_android.data.response.diary.MonthResponse
import app.junsu.onui_android.data.response.diary.RemindResponse
import app.junsu.onui_android.data.response.diary.WeekResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.time.LocalDate

interface DiaryApi {
    @POST("diary")
    suspend fun postDiary(
        @Body request: RemindRequest
    ): RemindResponse

    @Multipart
    @POST("img")
    suspend fun fetchImg(
        @Part file: MultipartBody.Part
    ): ImageResponse

    @GET("diary/ago")
    suspend fun fetchWeekDiary(): WeekResponse

    @GET("diary")
    suspend fun fetchMonthDiary(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): MonthResponse

    @GET("diary/detail")
    suspend fun fetchDayDiary(
        @Query("date") date: String,
    ): DayDiaryResponse
}