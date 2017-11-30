package com.nct.xmusicstation.data.model.database

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by Toan.IT
 * Date: 11/06/2017
 */

open class DBSinger : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    @Index
    var name_clean: String? = null
    @Index
    var shortname: String? = null
        set(shortname) {
            field = shortname
            this.sortnamelength = (shortname?.length ?: 0).toLong()

        }
    @Index
    var status: Int = 0
    var name: String? = null
    var count: Int = 0
    var image: String? = null
    var cover_image: String? = null
    private var sortnamelength: Long = 0
    var count_word: Int = 0
    val sortNameLength: Long
        get() {
            this.sortnamelength = (if (this.shortname == null) 0 else this.shortname!!.length).toLong()
            return this.sortnamelength
        }

}
