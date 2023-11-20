package app.junsu.onui_android.data.response.diary

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("message") val message: String,
)