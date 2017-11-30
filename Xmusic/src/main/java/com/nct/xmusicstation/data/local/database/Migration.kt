package com.nct.xmusicstation.data.local.database

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by Toan.IT on 10/27/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
class Migration : RealmMigration {
    protected val BaoOnline_Realm = "BaoOnline.realm"
    protected val RealmVersion = 1
    override fun migrate(dynamicRealm: DynamicRealm, l: Long, l1: Long) {

    }
}
