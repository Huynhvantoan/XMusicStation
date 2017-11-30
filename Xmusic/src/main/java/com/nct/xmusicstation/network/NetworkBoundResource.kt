package com.nct.xmusicstation.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.nct.xmusicstation.data.remote.api.ApiResponse
import com.nct.xmusicstation.ui.common.LiveRealmData
import io.realm.RealmModel
import io.realm.RealmResults
import java.util.*

@Suppress("LeakingThis")
abstract class NetworkBoundResource<Model : RealmModel, RequestType>
@MainThread
internal constructor() {

    private val result = MediatorLiveData<Resource<RealmResults<Model>>>()

    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { resultType ->
            result.removeSource(dbSource)
            if (shouldFetch(resultType)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<RealmResults<Model>>) {
        if (!Objects.equals(result.value, newValue)) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveRealmData<Model>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, { newData -> setValue(Resource.loading(newData)) })
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            if (response!!.isSuccessful) {
                ioThread {
                    processResponse(response)?.let { saveCallResult(it) }
                    mainThread {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb(),
                                { newData -> setValue(Resource.success(newData)) })
                    }
                }
            } else {
                onFetchFailed()
                result.addSource(dbSource)
                { newData -> result.value = response.errorMessage?.let { Resource.error(it, newData) } }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<RealmResults<Model>>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: ApiResponse<RequestType>): RequestType? {
        return response.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: RealmResults<Model>?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveRealmData<Model>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
