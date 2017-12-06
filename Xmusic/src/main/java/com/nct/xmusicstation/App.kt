package com.nct.xmusicstation

import android.arch.lifecycle.ProcessLifecycleOwner
import com.facebook.drawee.backends.pipeline.Fresco
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.nct.xmusicstation.data.local.prefs.PreferenceHelper
import com.nct.xmusicstation.di.applyAutoInjector
import com.nct.xmusicstation.di.component.DaggerAppComponent
import com.nct.xmusicstation.utils.ForegroundBackgroundListener
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.toan_itc.core.imageload.ImagePipelineConfigFactory
import dagger.android.support.DaggerApplication
import io.realm.Realm
import java.net.Proxy


class App : DaggerApplication() {
    private var instance: App? = null
    private var mRefWatcher: RefWatcher? = null
    private lateinit var appObserver: ForegroundBackgroundListener

    fun getInstance(): App? {
        return instance
    }

    fun getRefWatcher(): RefWatcher? {
        return mRefWatcher
    }

    override fun applicationInjector() = DaggerAppComponent.builder()
            .application(this)
            .build()

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()
        setupTest()
        setupData()
        initFileDownload()
        if (BuildConfig.DEBUG) {
            setupLogger()
        }
    }

    private fun setupLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(getString(R.string.app_name))
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        ProcessLifecycleOwner.get()
                .lifecycle
                .addObserver(ForegroundBackgroundListener()
                .also { appObserver = it })
    }

    private fun setupData() {
        PreferenceHelper.initialize(this,BuildConfig.APP_NAME)
        Realm.init(this)
        Fresco.initialize(this, ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this))
    }

    private fun initFileDownload() {
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(FileDownloadUrlConnection.Creator(FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000)
                        .readTimeout(15000)
                        .proxy(Proxy.NO_PROXY)
                ))
                .commit()
    }

    //Test
    fun setupTest(){
        if (LeakCanary.isInAnalyzerProcess(this)) return
        // init leak canary
        mRefWatcher = LeakCanary.install(this)
    }

}
