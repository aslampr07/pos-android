package com.kadbyte.client

import com.kadbyte.client.model.ImageUploadResponse
import com.kadbyte.client.model.Item
import com.kadbyte.client.model.Profile
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface KadByteService {

    @GET("/api/profile")
    suspend fun profile(): Response<Profile>

    @GET("/api/item")
    suspend fun getItemList(): Response<List<Item>>

    @POST("/api/item")
    suspend fun insertItem(@Body item: Item): Response<Item>

    @Multipart
    @POST("/api/item/image/upload")
    suspend fun uploadImage(@Part picture: MultipartBody.Part): Response<ImageUploadResponse>
}