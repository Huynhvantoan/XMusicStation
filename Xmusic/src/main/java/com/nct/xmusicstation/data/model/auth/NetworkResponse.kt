package com.nct.xmusicstation.data.model.auth

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.nct.xmusicstation.data.model.song.AlbumSchedule
import com.nct.xmusicstation.data.model.song.UserAccount

/**
 * Created by Toan.IT on 4/22/15.
 * Email:Huynhvantoan.itc@gmail.com
 */
class NetworkResponse {

    var status: String? = null
    var token: String? = null
    var user: UserAccount? = null
    @SerializedName("expires_at")
    var expiresAt: Long = 0
    var error: NetworkError? = null
    var data: JsonElement? = null
    var schedule: List<AlbumSchedule>? = null
    var code: String? = null
    @SerializedName("expires_in")
    var expiresIn: Int = 0
}
