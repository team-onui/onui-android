package app.junsu.onui_android.presentation.feature.graph

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.response.Analysis.AnalysisMonthlyResponse
import app.junsu.onui_android.data.response.Analysis.AnalysisMoodResponse

class GraphViewModel : ViewModel() {

    var analysisMoodResponse = AnalysisMoodResponse(0, 0, 0, 0, 0)
    var analysisMonthlyResponse = AnalysisMonthlyResponse(listOf(), "")

    suspend fun fetchAnalysis() {
        kotlin.runCatching {
            ApiProvider.analysisApi().fetchAnalysisMood()
        }.onSuccess {
            analysisMoodResponse = it
            Log.d("success", it.toString())
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun fetchAnalysisMonthly() {
        kotlin.runCatching {
            ApiProvider.analysisApi().fetchAnalysisMonthly()
        }.onSuccess {
            analysisMonthlyResponse = it
            Log.d("success", it.toString())
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }
}