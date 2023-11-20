package app.junsu.onui_android.data.response.Analysis

import com.google.gson.annotations.SerializedName

data class AnalysisMoodResponse(
    @SerializedName("worst") val worst: Int,
    @SerializedName("bad") val bad: Int,
    @SerializedName("not_bad") val notBad: Int,
    @SerializedName("fine") val fine: Int,
    @SerializedName("good") val good: Int,
)
