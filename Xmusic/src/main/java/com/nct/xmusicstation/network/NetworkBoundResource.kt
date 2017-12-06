package com.nct.xmusicstation.network

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@Suppress("LeakingThis")
abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {

    private val result = PublishSubject.create<Resource<ResultType>>()

    init {
        val dbSource = loadFromDb()
        dbSource.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({ value ->
                    //unsubscribe
                    dbSource.unsubscribeOn(Schedulers.io())

                    if (shouldFetch(value)) {
                        fetchFromNetwork()
                    } else {
                        result.onNext(Resource.success(value))
                    }
                })
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        //send a loading event
        result.onNext(Resource.loading(null))
        apiResponse
                .subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({ response ->
                    //unsubscribe apiResponse and dbSource (if any)
                    apiResponse.unsubscribeOn(Schedulers.io())

                    ioThread {
                        saveCallResult(response)
                        mainThread {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            val dbSource = loadFromDb()
                            dbSource.subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .take(1)
                                    .subscribe({
                                        dbSource.unsubscribeOn(Schedulers.io())
                                        result.onNext(Resource.success(it))
                                    })
                        }
                    }

                }, { error ->
                    error.printStackTrace()
                    onFetchFailed()
                    result.onNext(Resource.error(error.localizedMessage, null))
                })

    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @WorkerThread
    protected abstract fun createCall(): Flowable<RequestType>

}