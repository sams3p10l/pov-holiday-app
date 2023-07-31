package com.example.holidays.util

import com.example.holidays.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val originalUrl = chain.request().url

        val url = originalUrl.newBuilder()
            .addQueryParameter(PARAM_API_KEY, BuildConfig.API_KEY)
            .build()

        builder.url(url)

        return chain.proceed(builder.build())
    }

    companion object {
        private const val PARAM_API_KEY = "key"
    }
}