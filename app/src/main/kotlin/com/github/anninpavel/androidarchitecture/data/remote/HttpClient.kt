/*
 * Copyright 2018 Pavel Annin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.anninpavel.androidarchitecture.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/** @author Pavel Annin (https://github.com/anninpavel). */
class HttpClient(baseUrl: String, isDebugging: Boolean = false) {

    val retrofit by lazy { configRetrofit(baseUrl, isDebugging) }

    private fun configRetrofit(baseUrl: String, isDebugging: Boolean): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(configHttpClient(isDebugging))
            .build()
    }

    private fun configHttpClient(isDebugging: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(configLoggingInterceptor(isDebugging))
            .build()
    }

    private fun configLoggingInterceptor(isDebugging: Boolean): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (isDebugging) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    private companion object {
        private const val TIMEOUT_SECONDS = 60L
        private const val TIMEOUT_READ_SECONDS = 60L
        private const val TIMEOUT_WRITE_SECONDS = 2 * 60L
    }
}