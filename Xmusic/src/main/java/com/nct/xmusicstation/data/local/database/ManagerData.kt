package com.nct.xmusicstation.data.local.database

import com.nct.xmusicstation.data.model.Home
import com.nct.xmusicstation.data.model.SongNew
import io.reactivex.Flowable
import io.realm.Realm

/**
 * Created by Toan.IT on 11/3/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

interface RepositoryData {

    fun getRealmDB(): Realm

    fun insertDataSongNew(songNew: SongNew)

    fun insertDataHome(home: Home)

    fun getDataHome() : Flowable<Home>
}