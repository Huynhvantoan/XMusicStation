package com.nct.xmusicstation.ui.login

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import android.databinding.ObservableField
import com.nct.xmusicstation.data.HomeRepository

import com.toan_itc.core.base.BaseViewModel
import com.toan_itc.core.base.view.StatefulLayout
import javax.inject.Inject

class LoginViewModel
@Inject
internal constructor(homeRepository: HomeRepository) : BaseViewModel(), LifecycleObserver {

    val state = ObservableField<Int>()
    val message = ObservableField<String>()


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        // load data
        if (message.get() == null) loadData()
    }


    fun updateMessage() {
        var s = message.get()
        s += "o"
        message.set(s)
    }


    private fun loadData() {
        if (true) {
            // show progress
            state.set(StatefulLayout.PROGRESS)

            // load data
            Thread(Runnable {
                try {
                    Thread.sleep(2000L)
                    val s = "Hello"
                    onLoadData(s)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }).start()
        } else {
            state.set(StatefulLayout.OFFLINE)
        }
    }


    private fun onLoadData(s: String) {
        // save data
        message.set(s)

        // show content
        if (message.get() != null) {
            state.set(StatefulLayout.CONTENT)
        } else {
            state.set(StatefulLayout.EMPTY)
        }
    }
}
