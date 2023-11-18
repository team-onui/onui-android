package app.junsu.onui_android.data.response.user

import com.google.gson.annotations.SerializedName

data class BuyThemeResponse(
    @SerializedName("theme_list") val themeList: List<Themes>?
) {
    data class Themes(
        @SerializedName("theme_id") val themeId: String,
        @SerializedName("price") val price: Long,
    )
}
