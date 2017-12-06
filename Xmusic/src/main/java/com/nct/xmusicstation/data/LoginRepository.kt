package com.nct.xmusicstation.data

import com.nct.xmusicstation.data.local.database.RealmManager
import com.nct.xmusicstation.data.model.song.LoginCode
import com.nct.xmusicstation.data.remote.api.ApiService
import com.nct.xmusicstation.network.NetworkBoundResource
import com.nct.xmusicstation.network.RateLimiter
import com.nct.xmusicstation.network.Resource
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Toan.IT on 11/3/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Singleton
class LoginRepository
@Inject
constructor(val realmManager: RealmManager, val apiService: ApiService) {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    /*fun loadHome(id_occasion: String, lang: String): Flowable<Resource<Home>> {
        return object : NetworkBoundResource<Home, Home>() {
            override fun saveCallResult(item: Home) {
                Logger.e("saveCallResult")
                realmManager.insertDataHome(item)
            }

            override fun shouldFetch(data: Home?): Boolean {
                Logger.e("shouldFetch"+(data == null || repoListRateLimit.shouldFetch(id_occasion)))
                return (data == null || repoListRateLimit.shouldFetch(id_occasion))
            }

            override fun loadFromDb(): Flowable<Home> {
                Logger.e("loadFromDb")
               *//* realmManager.getDataHome().map { for (i in 0..19){
                    Logger.e(""+it.data?.song_recommend?.data?.get(i)?.name.toString())
                } }.subscribe {  }*//*
                return realmManager.getDataHome()
            }

            override fun createCall(): Flowable<Home> {
                Logger.e("createCall")
                return apiService.getDataHome(id_occasion, lang, 1, 20)
            }

            override fun onFetchFailed() {
                Logger.e("onFetchFailed")
                repoListRateLimit.reset(id_occasion)
            }
        }.asFlowable()
    }*/

    fun getLoginCode(): Flowable<Resource<LoginCode>>{
        val timestamp = System.currentTimeMillis()
        val md5 = AppJNI.getMD5(timestamp.toString(), XmusicApp.deviceInfo.deviceID)
        return request(
                Observable.defer({
                    xmsApi.getLoginCode(deviceInfoString, XmusicApp.accessToken,
                            timestamp, md5)
                }))
                .map({ networkResponse ->
                    val loginCode = LoginCode()
                    loginCode.code = networkResponse.code
                    loginCode.expiresIn = networkResponse.expiresIn
                    loginCode
                })
    }.asFlow

}