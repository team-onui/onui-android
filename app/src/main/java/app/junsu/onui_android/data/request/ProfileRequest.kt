package app.junsu.onui_android.data.request

import com.google.gson.annotations.SerializedName

data class ProfileRequest(
    @SerializedName("sub") val sub: String,
    @SerializedName("name") val name: String,
    @SerializedName("theme") val theme: String,
    @SerializedName("on_filtering") val onFiltering: Boolean,
)