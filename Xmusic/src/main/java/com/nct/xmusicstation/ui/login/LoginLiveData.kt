package com.nct.xmusicstation.ui.login

import android.arch.lifecycle.LiveData
import com.nct.xmusicstation.network.Resource
import io.reactivex.disposables.Disposable

/**
 * Created by Toan.IT on 12/6/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
/*
open class LoginLiveData(LoginRepo, owner:String):
        LiveData<Resource<List<Repo>>>() {
    private var disposable: Disposable? = null

    init {
        disposable = repoRepository.loadRepos(owner).subscribe({
            data ->
            value = data
        })
    }

    override fun onInactive() {
        super.onInactive()
        if(disposable?.isDisposed?.not() == true){
            disposable?.dispose()
        }
    }
}*/
