package com.nct.xmusicstation.di.module

import com.nct.xmusicstation.data.local.database.RealmManager
import com.nct.xmusicstation.data.remote.download.DownloadManager
import com.nct.xmusicstation.library.eventbus.RxBus
import com.toan_itc.core.imageload.FrescoImageLoader
import com.toan_itc.core.imageload.ImageLoaderListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Toan.IT
 * Date: 11/1/2017
 * Email: huynhvantoan.itc@gmail.com
 */
@Module
class DataModule {

    @Singleton
    @Provides
    internal fun realmManager(): RealmManager {
        return RealmManager(downloadManager())
    }

    @Singleton
    @Provides
    internal fun downloadManager(): DownloadManager {
        return DownloadManager()
    }

    @Singleton
    @Provides
    internal fun rxBus(): RxBus {
        return RxBus()
    }

    @Singleton
    @Provides
    internal fun imageLoaderListener(): ImageLoaderListener {
        return FrescoImageLoader()
    }
}
