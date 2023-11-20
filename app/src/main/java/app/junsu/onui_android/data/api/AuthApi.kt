package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.request.LoginRequest
import app.junsu.onui_android.data.response.user.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @GET("auth/test")
    suspend fun getToken(): String

    @POST("auth/google/v2")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}