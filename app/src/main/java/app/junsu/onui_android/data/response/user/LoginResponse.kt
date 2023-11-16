package app.junsu.onui_android.data.response.user

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expire_at") val accessTokenExpireAt: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expire_at") val refreshTokenExpireAt: String,
)
