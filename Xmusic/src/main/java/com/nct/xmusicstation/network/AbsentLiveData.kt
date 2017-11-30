package com.nct.xmusicstation.network

import android.arch.lifecycle.LiveData

/**
 * Created by Toan.IT on 10/23/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

class AbsentLiveData<T>
private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
