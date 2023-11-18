package app.junsu.onui_android.data.response.user

import com.google.gson.annotations.SerializedName

data class ThemeResponse(
    @SerializedName("theme_list") val themeList: List<Theme>,
) {
    data class Theme(
        val theme: String,
    )
}
