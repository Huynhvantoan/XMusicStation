package com.nct.xmusicstation.data.remote.api

import com.nct.xmusicstation.data.model.auth.NetworkResponse
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Toan.IT on 12/06/17.
 * Email:Huynhvantoan.itc@gmail.com
 * REST API access points
 */
interface ApiService {
    //Auth
    @FormUrlEncoded
    @POST("auth/token")
    fun getAccessToken(
            @Field("device_info") deviceInfo: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("grant_type") grantType: String): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("auth/token")
    fun refreshToken(
            @Field("device_info") deviceInfo: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("grant_type") grantType: String,
            @Field("token") token: String): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
            @Field("device_info") deviceInfo: String,
            @Field("username") username: String,
            @Field("password") password: String): Flowable<NetworkResponse>

    //Common
    @FormUrlEncoded
    @POST("common/update_version")
    fun getUpdateVersion(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String): Flowable<NetworkResponse>

    //XMS
    @FormUrlEncoded
    @POST("/code/generate")
    fun getLoginCode(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String,
            @Field("t") timestamp: Long,
            @Field("e") md5: String): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("/code/retrieve")
    fun checkLogin(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String,
            @Field("code") code: String): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("showcase/album")
    fun getShowcaseAlbums(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String,
            @Field("page_index") pageIndex: Int,
            @Field("page_size") pageSize: Int): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("album/user")
    fun getBrandAlbums(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String,
            @Field("page_index") pageIndex: Int,
            @Field("page_size") pageSize: Int): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("album/detail")
    fun getAlbumDetail(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String,
            @Field("id") albumId: Long): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("user/profile")
    fun getUserProfile(@Field("device_info") deviceInfo: String,
                       @Field("token") token: String): Flowable<NetworkResponse>

    @FormUrlEncoded
    @POST("tracking/play")
    fun trackPlaySong(
            @Field("device_info") deviceInfo: String,
            @Field("token") token: String,
            @Field("data") data: String): Flowable<NetworkResponse>

}
