package com.nct.xmusicstation.data.model

import com.nct.xmusicstation.define.UpdateDef
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Home : RealmObject() {
    var code: Int = 0
    var version: Int = 0
    var versionDB: Int = 0
    var is_more: Int = 0
    var data: Data? = null
    var message: String? = null
}
open class Data : RealmObject() {
    var song_recommend: SongRecommend? = null
    var song_new: SongNew? = null
    var playlist: HomePlaylist? = null
    var singer: HomeSinger? = null
}

open class SongNew : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var data: RealmList<DataItem>? = null
    var name: String? = null
}

open class SongRecommend : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var data: RealmList<DataItem>? = null
    var name: String? = null
}

open class HomeSinger : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var data: RealmList<DataItem>? = null
    var name: String? = null
}

open class HomePlaylist : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var data: RealmList<DataItem>? = null
    var name: String? = null
}

open class DataItem : RealmObject() {
    var id: Int = 0
    var name: String? = null
    var name_clean: String? = null
    var type: String? = null
    var shortname: String? = null
    var lyric_text: String? = null
    var lyric_text_clean: String? = null
    var format: Int = 0
    var count: Int =0
    var cate :String? = null
    var is_like : Int =0
    var singer_id: String? = null
    var composer_id: String? = null
    var singer: String? = null
    var composer: String? = null
    var tag: String? = null
    var tag_search: String? = null
    var list_mv_default: String? = null
    var occasion_list: String? = null
    var image: String? = null
    var cover_image : String? = null
    var thumbnail: String? = null
    var beat: String? = null
    var mp3: String? = null
    var mv: String? = null
    var lyric: String? = null
    var dual: Int = 0
    var delete_status: Int = 0
    var status :Int =0
    var license_song: Int = 0
    var license_mp3: Int = 0
    var license_mv: Int = 0
    var lang: String? = null
    var isUpdate = UpdateDef.UPDATE_FALSE
}
