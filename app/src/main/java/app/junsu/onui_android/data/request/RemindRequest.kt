package app.junsu.onui_android.data.request

import app.junsu.onui_android.Mood

data class RemindRequest(
    val content: String,
    val mood: Mood,
    val tag_list: List<String>,
    val image: String?,
)
