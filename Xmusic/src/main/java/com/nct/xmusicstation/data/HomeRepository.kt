package com.nct.xmusicstation.data

import com.nct.xmusicstation.data.local.database.RealmManager
import com.nct.xmusicstation.data.model.Home
import com.nct.xmusicstation.data.remote.api.ApiService
import com.nct.xmusicstation.network.RateLimiter
import com.nct.xmusicstation.network.Resource
import com.nct.xmusicstation.network.RxNetworkBoundResource
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
class HomeRepository
@Inject
constructor(val realmManager: RealmManager, val apiService: ApiService) {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    fun loadHome(id_occasion: String, lang: String): Flowable<Resource<Home>> {
        return object : RxNetworkBoundResource<Home, Home>() {
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
               /* realmManager.getDataHome().map { for (i in 0..19){
                    Logger.e(""+it.data?.song_recommend?.data?.get(i)?.name.toString())
                } }.subscribe {  }*/
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
    }
    /*fun loadHome(id_occasion: String, lang: String): LiveData<Resource<RealmResults<Home>>> {
        return object : NetworkBoundResource<Home, Home>() {
            override fun saveCallResult(item: Home) {
                Logger.e("saveCallResult")
                realmManager.insertDataHome(item)
            }

            override fun shouldFetch(data: RealmResults<Home>?): Boolean {
                *//*Logger.e("shouldFetch"+(data == null || data.isEmpty() || repoListRateLimit.shouldFetch(id_occasion)))
                return (data == null || data.isEmpty() || repoListRateLimit.shouldFetch(id_occasion))*//*
                return data==null
            }

            override fun loadFromDb(): LiveRealmData<Home> {
                Logger.e("loadFromDb::"+realmManager.getDataHome()?.value!![0]?.data?.song_new?.data.toString())
                return realmManager.getDataHome()
            }

            override fun createCall(): LiveData<ApiResponse<Home>> {
                Logger.e("createCall")
                return apiService.getDataHome(id_occasion, lang, 1, 20)
            }

            override fun onFetchFailed() {
                Logger.e("onFetchFailed")
                repoListRateLimit.reset(id_occasion)
            }
        }.asLiveData()
    }*/
}