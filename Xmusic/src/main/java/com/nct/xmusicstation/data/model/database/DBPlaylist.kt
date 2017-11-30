package com.nct.xmusicstation.data.model.database

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by Toan.IT
 * Date: 11/06/2017
 */

open class DBPlaylist : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String? = null
    @Index
    var name_clean: String? = null
    var singer: String? = null
    var image: String? = null
    var list_songs: String? = null
}
