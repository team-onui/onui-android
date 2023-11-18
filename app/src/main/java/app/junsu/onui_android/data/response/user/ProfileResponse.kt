package app.junsu.onui_android.data.response.user

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("sub") val sub: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_theme") val profileTheme: String,
    @SerializedName("theme") val theme: String,
    @SerializedName("on_filtering") val onFiltering: Boolean,
)