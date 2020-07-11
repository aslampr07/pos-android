package com.kadbyte.client

import com.kadbyte.client.model.Item
import com.kadbyte.client.model.Profile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KadByteService {

    @GET("/api/profile")
    suspend fun profile(): Response<Profile>

    @GET("/api/item")
    suspend fun getItemList(): Response<List<Item>>

    @POST("/api/item")
    suspend fun insertItem(@Body item: Item): Response<Item>
}