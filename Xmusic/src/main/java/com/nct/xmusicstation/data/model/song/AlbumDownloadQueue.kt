package com.nct.xmusicstation.data.model.song

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
* Created by Toan.IT on 3/6/17.
* Email:Huynhvantoan.itc@gmail.com
*/

class AlbumDownloadQueue : RealmObject() {
    @PrimaryKey
    var albumId: Long = 0
}
