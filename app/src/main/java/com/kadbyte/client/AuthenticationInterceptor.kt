package com.kadbyte.client

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(
    private val preferences: SharedPreferences,
    private val authenticationService: AuthenticationService
) : Interceptor {

    private val TAG = "AuthenticationInterc"

    private var accessToken: String? = null

    init {
        Log.v(TAG, "Constructor invoked")
        mutableListOf<String>()
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()


        if (accessToken == null) {
            val token = preferences.getString("refreshToken", null)
            Log.v("exchangeRequest", "Called")
            if (token != null) {
                val response = authenticationService.exchange(token).execute()
                if (response.isSuccessful) {
                    accessToken = response.body()?.token
                }
            }
        }

        builder.addHeader(
            "Authorization",
            "Bearer $accessToken"
        )

        return chain.proceed(builder.build())
    }

}