package com.nct.xmusicstation.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.nct.xmusicstation.BuildConfig
import com.nct.xmusicstation.data.remote.api.ApiService
import com.nct.xmusicstation.data.remote.api.retrofit.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Toan.IT on 11/1/17.
 * Email: huynhvantoan.itc@gmail.com
 */

@Module
internal class NetworkModule {

    @Singleton
    @Provides
    fun sRestClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun sRetrofit(okHttpClient: OkHttpClient, gson: Gson,rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        val builder = GsonBuilder()
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss")
        return builder.create()
    }

    @Singleton
    @Provides
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("request")
                .response("response")
                .addHeader("Authorization", Credentials.basic(BuildConfig.API_USER, BuildConfig.API_PASSWORD))
                .addHeader("token", "3819efc20135c641eeda425b105cc922")
                .build())
        return client.build()
    }
}
