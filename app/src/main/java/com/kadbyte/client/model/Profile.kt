package com.kadbyte.client.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Profile(
    val username: String,
    val phone: String,
    val email: String,
    val stores: List<Store>
)