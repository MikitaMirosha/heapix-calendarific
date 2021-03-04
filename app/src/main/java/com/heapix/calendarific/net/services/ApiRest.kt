package com.heapix.calendarific.net.services

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiRest {

    companion object {

        private const val BASE_URL: String = "https://calendarific.com/api/v2/"
        private const val API_KEY: String = "695c69c7414e935be7cc43d17666bbe916b62863"

        internal fun getApi(): Retrofit {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC

            val client = getOkHttpClient()

            val builder = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(getGsonConverterFactory())
                .client(client)

            return builder.build()
        }

        private fun getGsonConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create(GsonBuilder().setLenient().create())
        }

        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient()
                .newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    val originalHttpUrl = chain.request().url
                    val url = originalHttpUrl
                        .newBuilder()
                        .addQueryParameter(
                            "api_key",
                            API_KEY
                        )
                        .build()
                    request.url(url)
                    return@addInterceptor chain.proceed(request.build())
                }
                .addNetworkInterceptor(Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    chain.proceed(requestBuilder.build())
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        }
    }
}