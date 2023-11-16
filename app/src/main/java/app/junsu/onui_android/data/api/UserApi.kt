package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.request.FilterRequest
import app.junsu.onui_android.data.request.ProfileRequest
import app.junsu.onui_android.data.request.ThemeRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserApi {

    @GET("auth/test")
    suspend fun getToken(): String

    @GET("user/profile")
    suspend fun fetchProfile(): ProfileRequest

    @PATCH("user/filter")
    suspend fun changeFilter(
        @Body filterRequest: FilterRequest
    ): ProfileRequest

    @PATCH("user/theme")
    suspend fun changeTheme(
        @Body themeRequest: ThemeRequest
    ): ProfileRequest

    @DELETE("auth")
    suspend fun deleteUser(): Void
}