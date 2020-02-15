package com.kadbyte.client

import com.kadbyte.client.model.ServiceError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideService(): KadByteService {

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(logger)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.kadbyte.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit.create(KadByteService::class.java)
    }


    @Provides
    @Singleton
    fun provideAdapter(): JsonAdapter<List<ServiceError>> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, ServiceError::class.java)
        return moshi.adapter(type)
    }
}