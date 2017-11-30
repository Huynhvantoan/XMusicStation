package com.nct.xmusicstation.data.model.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by toanit on 11/06/17.
 * Email:huynhvantoan.itc@gmail.com
 */

open class DBEmotion : RealmObject() {
    /**
     * id_emotion : 22
     * image : 2016_11_11_e_4_e4443210c3a04682b0b209b576ce8a67.png
     * score : 30
     * status : 1
     */
    @PrimaryKey
    var id_emotion: Int = 0
    var image: String? = null
    var score: Int = 0
    var status: Int = 0
}
