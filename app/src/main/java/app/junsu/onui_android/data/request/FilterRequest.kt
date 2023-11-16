package app.junsu.onui_android.data.request

import com.google.gson.annotations.SerializedName

data class FilterRequest(
    @SerializedName("on_filtering") val onFiltering: Boolean
)
