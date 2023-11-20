package app.junsu.onui_android.data.request

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @SerializedName("tag_list") val tagList: List<String>
)
