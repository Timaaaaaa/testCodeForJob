package com.thousand.aidynnury.global.di

import com.thousand.aidynnury.global.di.ServiceProperties.SERVER_URL
import com.thousand.aidynnury.global.service.ServerService
import com.thousand.aidynnury.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<ServerService>(get(), SERVER_URL) }
}


object ServiceProperties {
    const val SERVER_URL = "http://194.4.58.92/"
    const val SERVER_URL_IMAGE = "http://194.4.58.92/"
    const val AUTH_HEADER = ""
}

fun createOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()

    okHttpClientBuilder.addInterceptor { chain ->
        var request = chain.request()
        val url = request.url().newBuilder()
        request = request.newBuilder()
            //.addHeader(AUTH_HEADER, tokenStorage.getToken())
            .url(url.build())
            .build()
        chain.proceed(request)
    }

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
    }
    return okHttpClientBuilder.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}