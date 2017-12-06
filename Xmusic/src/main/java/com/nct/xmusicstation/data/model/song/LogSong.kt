package com.nct.xmusicstation.data.model.song

import io.realm.RealmObject

/**
* Created by Toan.IT on 6/16/17.
* Email:Huynhvantoan.itc@gmail.com
*/

class LogSong : RealmObject() {

    var key: String? = null
    var timestamp: Long = 0
}
