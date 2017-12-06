package com.nct.xmusicstation.data.model.song

/**
 * Created by ducth on 2/21/17.
 */

class UserAccount {

    var id: Long = 0
    var username: String? = null
    var fullname: String? = null
    var email: String? = null
    var address: String? = null
    var phone: String? = null
    var imageAvatar: String? = null
    var imageCover: String? = null

    companion object {
        val DEFAULT_USER_ID = 0
    }
}
