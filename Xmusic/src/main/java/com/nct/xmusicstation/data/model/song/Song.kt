package com.nct.xmusicstation.data.model.song

import io.realm.RealmObject
import io.realm.annotations.Ignore

/**
* Created by Toan.IT on 12/06/17.
* Email:Huynhvantoan.itc@gmail.com
*/

class Song : RealmObject() {
    var key: String? = null

    var title: String? = null
    @Ignore
    var artists: List<String>? = null
    var streamUrl: String? = null
    var duration: Int = 0
    // public long modifiedAt;

    /** Internal use  */
    var ordinal: Int = 0
    var albumId: Long = -1
    var albumName: String? = null
    var artistNames: String? = null
    var downloaded: Boolean = false
}
