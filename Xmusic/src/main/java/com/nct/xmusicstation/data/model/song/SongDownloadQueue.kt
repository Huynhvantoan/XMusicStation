package com.nct.xmusicstation.data.model.song

import io.realm.RealmObject

/**
* Created by Toan.IT on 3/6/17.
* Email:Huynhvantoan.itc@gmail.com
*/

class SongDownloadQueue : RealmObject() {

    var songOrdinal: Long = 0
    var albumId: Long = 0
    var albumName: String? = null
    var songName: String? = null
    var songKey: String? = null
    var streamUrl: String? = null

    var batchId: Long = 0
}
