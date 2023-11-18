package app.junsu.onui_android.data.request

import com.google.gson.annotations.SerializedName

data class ThemeRequest(
    val theme: String,
)

data class ProfileThemeRequest(
    @SerializedName("profile_theme") val profileTheme: String,
)