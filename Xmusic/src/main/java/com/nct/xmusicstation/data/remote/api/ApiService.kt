package com.nct.xmusicstation.data.remote.api

import android.arch.lifecycle.LiveData
import com.nct.xmusicstation.data.model.Home
import com.nct.xmusicstation.data.model.Welcome
import com.nct.xmusicstation.data.model.register.CheckRegister
import com.nct.xmusicstation.data.model.tracking.UpdateApp
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by Toan.IT on 10/19/17.
 * Email:Huynhvantoan.itc@gmail.com
 * REST API access points
 */
interface ApiService {

    @GET
    fun checkUpdateApp(@Url url: String): LiveData<ApiResponse<UpdateApp>>

    @FormUrlEncoded
    @POST("box-active")
    fun checkRegister(@Field("android_id") android_id: String, @Field("serial_no") serial_no: String): LiveData<ApiResponse<CheckRegister>>

    @POST("box-welcome")
    fun getWelcome(): LiveData<ApiResponse<Welcome>>

    //http://api.karaokecuatui.vn/api/box-home-new-update?id_occasion=32&lang=vi&page_index=1&page_size=20
    @GET("box-home-new-update")
    fun getDataHome(@Query("id_occasion") id_occasion: String,
                    @Query("lang") lang: String,
                    @Query("page_index") page_index: Int,
                    @Query("page_size") page_size: Int): Flowable<Home>

}
