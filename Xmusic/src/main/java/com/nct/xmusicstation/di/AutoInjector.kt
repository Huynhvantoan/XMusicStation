package com.nct.xmusicstation.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.toan_itc.core.base.BaseBindingActivity
import com.toan_itc.core.base.BindingActivity
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

interface Injectable

fun Application.applyAutoInjector() = registerActivityLifecycleCallbacks(
        object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })

private fun handleActivity(activity: Activity) {
    when (activity) {
        is BaseBindingActivity<*, *> -> AndroidInjection.inject(activity)
        is FragmentActivity -> {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
                            super.onFragmentCreated(fm, f, savedInstanceState)
                            AndroidSupportInjection.inject(f)
                        }
                    }, true)
        }
    }
    /*if (activity is Injectable || activity is HasSupportFragmentInjector) {
      AndroidInjection.inject(activity)
    }
    if (activity is FragmentActivity) {
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
          object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager, f: BaseFragment, s: Bundle?) {
              if (f is Injectable) {
                AndroidSupportInjection.inject(f)
              }
            }
          }, true)
    }*/
}
