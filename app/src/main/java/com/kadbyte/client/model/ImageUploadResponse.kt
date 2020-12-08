package com.kadbyte.client.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUploadResponse(val assetId: String)