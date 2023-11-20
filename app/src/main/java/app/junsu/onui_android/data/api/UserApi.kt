package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.request.FilterRequest
import app.junsu.onui_android.data.request.NameRequest
import app.junsu.onui_android.data.request.ProfileThemeRequest
import app.junsu.onui_android.data.request.ThemeRequest
import app.junsu.onui_android.data.response.user.AllThemeResponse
import app.junsu.onui_android.data.response.user.BuyThemeResponse
import app.junsu.onui_android.data.response.user.ProfileResponse
import app.junsu.onui_android.data.response.user.RiceResponse
import app.junsu.onui_android.data.response.user.ThemeResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("user/profile")
    suspend fun fetchProfile(): ProfileResponse

    @PATCH("user/filter")
    suspend fun changeFilter(
        @Body filterRequest: FilterRequest
    ): ProfileResponse

    @PATCH("user/theme")
    suspend fun changeTheme(
        @Body themeRequest: ThemeRequest
    ): ProfileResponse

    @DELETE("auth")
    suspend fun deleteUser(): Void

    @GET("user/bought/theme")
    suspend fun getTheme(): ThemeResponse

    @PATCH("user/profile")
    suspend fun patchProfileTheme(
        @Body profileThemeRequest: ProfileThemeRequest
    ): ProfileResponse

    @PATCH("user/rename")
    suspend fun patchName(
        @Body nameRequest: NameRequest
    ): ProfileResponse

    @POST("shop/buy")
    suspend fun buyTheme(
        @Query("id") id: String,
    ): BuyThemeResponse

    @GET("user/rice")
    suspend fun fetchRice(): RiceResponse

    @GET("shop/all")
    suspend fun fetchAllTheme(): AllThemeResponse
}