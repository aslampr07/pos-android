package com.kadbyte.client

import com.kadbyte.client.model.SignIn
import com.kadbyte.client.model.SignUp
import com.kadbyte.client.model.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationService {
    @POST("/api/account/create")
    suspend fun signUp(@Body signUp: SignUp): Call<Boolean>
    @POST("/api/account/login")
    suspend fun logIn(@Body signIn: SignIn): Response<TokenResponse>
    @GET("/api/account/exchange")
    fun exchange(@Query("token") token: String): Call<TokenResponse>
}