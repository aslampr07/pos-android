package com.kadbyte.client.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUp(val username: String,
                  val password: String,
                  val phone: String,
                  val email: String)