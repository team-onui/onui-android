package app.junsu.onui_android.data.response.user

import com.google.gson.annotations.SerializedName

data class AllThemeResponse(
    @SerializedName("theme_list") val themeList: List<AllThemes>,
) {
    data class AllThemes(
        @SerializedName("theme") val theme: String,
        @SerializedName("price") val price: Long,
        @SerializedName("is_bought") val isBought: Boolean,
    )
}
