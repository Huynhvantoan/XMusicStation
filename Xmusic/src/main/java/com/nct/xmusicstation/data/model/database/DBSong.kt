package com.nct.xmusicstation.data.model.database

import com.google.gson.annotations.SerializedName
import com.nct.xmusicstation.define.UpdateDef
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by Toan.IT
 * Date: 11/06/2017
 */

open class DBSong : RealmObject() {
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
    var count_word: Int = 0
    var lyric_text_clean: String? = null
    var tag_search: String? = null
    var delete_status: Int = 0
    var name: String? = null
    var lyric_text: String? = null
    var lyric: String? = null
    var format: Int = 0
    var singer: String? = null
    var composer: String? = null
    var image: String? = null
    var thumbnail: String? = null
    var beat: String? = null
    var mp3: String? = null
    var mv: String? = null
    var dual: Int = 0
    @SerializedName("updated_time")
    var updated_time: Date? = null
    var category_list: String? = null
    var occasion_list: String? = null
    var lang: String? = null
    var singer_id: String? = null
    var tag: String? = null
    var update_media: String? = null
    var license_mv: Int = 0
    var license_mp3: Int = 0
    var license_song: Int = 0
    var update = UpdateDef.UPDATE_FALSE
    private var sortnamelength: Long = 0
    val sortNameLength: Long
        get() {
            this.sortnamelength = (if (this.shortname == null) 0 else this.shortname!!.length).toLong()
            return this.sortnamelength
        }
}