package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.response.Analysis.AnalysisMonthlyResponse
import app.junsu.onui_android.data.response.Analysis.AnalysisMoodResponse
import retrofit2.http.GET

interface AnalysisApi {

    @GET("analysis/mood")
    suspend fun fetchAnalysisMood(): AnalysisMoodResponse

    @GET("analysis/monthly")
    suspend fun fetchAnalysisMonthly(): AnalysisMonthlyResponse
}