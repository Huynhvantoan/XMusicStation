package com.nct.xmusicstation.ui.common

import android.arch.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults


/**
 * Created by Toan.IT on 10/19/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

class LiveRealmData<T : RealmModel>(private val results: RealmResults<T>) : LiveData<RealmResults<T>>() {
    private val listener = RealmChangeListener<RealmResults<T>> { results ->
        value = results
    }

    override fun onActive() {
        results.addChangeListener(listener)
        value = results
    }

    override fun onInactive() {
        results.removeChangeListener(listener)
    }
}

