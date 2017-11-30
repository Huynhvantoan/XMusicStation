package com.nct.xmusicstation.data.local.database


import com.nct.xmusicstation.data.model.Home
import com.nct.xmusicstation.data.model.SongNew
import com.nct.xmusicstation.data.remote.download.DownloadManager
import com.nct.xmusicstation.utils.Constants
import com.toan_itc.core.realm.queryAllAsFlowable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

/**
 * Created by Toan.IT on 11/04/17.
 * Email: huynhvantoan.itc@gmail.com
 */

class RealmManager
@Inject
constructor(private val downloadManager: DownloadManager) : RepositoryData {
    private val mRealmConfigBox: RealmConfiguration
    private val mRealmConfigDB: RealmConfiguration

    init {
        mRealmConfigDB = RealmConfiguration.Builder()
                .name(Constants.DB_Realm)
                .assetFile(Constants.DB_Realm)
                .schemaVersion(Constants.RealmVersion)
                .build()
        mRealmConfigBox = RealmConfiguration.Builder()
                .name(Constants.KCTBox_Realm)
                .schemaVersion(Constants.RealmVersion)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(mRealmConfigDB)
    }

    override fun getRealmBox(): Realm {
        return Realm.getInstance(mRealmConfigBox)
    }

    override fun getRealmDB(): Realm {
        return Realm.getInstance(mRealmConfigDB)
    }

    override fun insertDataSongNew(songNew: SongNew) {
        getRealmDB().executeTransactionAsync{
            realm: Realm? -> realm?.copyToRealmOrUpdate(songNew)
        }
    }
    override fun insertDataHome(home: Home) {
            getRealmDB().executeTransactionAsync{
                    realm: Realm? -> realm?.copyToRealmOrUpdate(home)
            }
    }

    override fun getDataHome(): Flowable<Home> {
       /* Logger.e("HomeUpdate="+ getRealmDB().where(Home::class.java)
                .findFirst()?.data?.song_new.toString())*/
        return Home().queryAllAsFlowable().map { it-> it[0] }
    }
}
