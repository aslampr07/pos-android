package com.kadbyte.client

import com.kadbyte.client.model.Profile
import retrofit2.Response
import retrofit2.http.GET

interface KadByteService {

    @GET("/api/profile")
    suspend fun profile(): Response<Profile>
}