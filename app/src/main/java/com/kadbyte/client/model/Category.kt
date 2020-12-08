package com.kadbyte.client.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(val code: String, val name: String)