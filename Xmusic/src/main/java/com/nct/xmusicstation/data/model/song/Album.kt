package com.nct.xmusicstation.data.model.song

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore

/**
 * Created by Toan.IT on 2/22/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

class Album : RealmObject() {
    var name: String? = null
    // public String desc;
    var image: String? = null
    @Ignore
    var songDetails: List<Song>? = null
    // public int totalSongs;
    // public long createdAt;
    // public long modifiedAt;
    // public String type;
    var shuffle: Boolean = false

    /** Internal use  */
    var ordinal: Int = 0
    var userAccountId = UserAccount.DEFAULT_USER_ID.toLong()
    var downloaded: Boolean = false
    var type: AlbumType? = null

    enum class AlbumType {
        @SerializedName("PUBLIC")
        PUBLIC,
        @SerializedName("PRIVATE")
        PRIVATE
    }
}
