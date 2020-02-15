package com.kadbyte.client

import com.kadbyte.client.model.SignUp
import com.kadbyte.client.model.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KadByteService {
    @POST("/api/account/create")
    fun signUp(@Body signUp: SignUp): Call<Boolean>
    @POST("/api/account/login")
    suspend fun logIn(): Response<TokenResponse>
    @GET("/api/account/exchange")
    suspend fun exchange(@Query("token") token: String): Response<TokenResponse>
}