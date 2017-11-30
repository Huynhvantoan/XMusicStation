@file:JvmName("RealmUtils")

// pretty name for utils class if called from
package com.nct.xmusicstation.utils

import com.nct.xmusicstation.ui.common.LiveRealmData
import io.realm.RealmModel
import io.realm.RealmResults

/**
 * Created by Toan.IT on 10/19/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

fun <T : RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)

