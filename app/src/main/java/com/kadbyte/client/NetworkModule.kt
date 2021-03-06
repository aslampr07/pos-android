package com.kadbyte.client

import android.content.Context
import android.content.SharedPreferences
import com.kadbyte.client.model.ServiceError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAuthenticationService(): AuthenticationService {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().addInterceptor(logger)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.kadbyte.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    fun provideKadByteService(interceptor: AuthenticationInterceptor): KadByteService {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC

        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.kadbyte.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit.create(KadByteService::class.java)
    }

    @Provides
    fun provideAdapter(): JsonAdapter<List<ServiceError>> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, ServiceError::class.java)
        return moshi.adapter(type)
    }

    @Provides
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
}