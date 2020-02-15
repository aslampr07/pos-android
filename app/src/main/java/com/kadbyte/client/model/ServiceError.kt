package com.kadbyte.client.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServiceError(
    val code: Int,
    val field: String?,
    val message: String
)