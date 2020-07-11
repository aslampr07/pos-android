package com.kadbyte.client.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    val code: String,
    val name: String,
    val alias: String,
    val sellingPrice: Float,
    val images: List<String>,
    val createdAt: String,
    val color: String
)