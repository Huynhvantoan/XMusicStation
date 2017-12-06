package com.nct.xmusicstation.data.model.song

import com.google.gson.annotations.SerializedName

/**
 * Created by ducth on 7/12/17.
 */

class LoginCode {

    var code: String? = null
    @SerializedName("expires_in")
    var expiresIn: Int = 0
}
