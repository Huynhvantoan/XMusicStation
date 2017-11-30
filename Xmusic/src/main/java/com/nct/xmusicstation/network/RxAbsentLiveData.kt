package com.nct.xmusicstation.network

import com.nct.xmusicstation.data.HomeRepository
import com.nct.xmusicstation.viewmodel.HomeLiveData

/**
 * Created by Toan.IT on 10/23/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

class RxAbsentLiveData private constructor(repository: HomeRepository, id_occasion: String, lang:String) : HomeLiveData(repository, id_occasion, lang) {
    init {
        postValue(null)
    }

    companion object {
        fun create(repository: HomeRepository): RxAbsentLiveData {
            return RxAbsentLiveData(repository, "","")
        }
    }
}
