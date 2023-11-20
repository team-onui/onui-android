package app.junsu.onui_android.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("sub") val sub: String,
    @SerializedName("name") val name: String,
)
