package com.nct.xmusicstation.ui.base.ui

import android.os.Bundle
import com.nct.xmusicstation.App

import com.toan_itc.core.base.CoreActivity

abstract class BaseActivity : CoreActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    public override fun onStart() {
        super.onStart()
    }


    public override fun onResume() {
        super.onResume()
    }


    public override fun onPause() {
        super.onPause()
    }


    public override fun onStop() {
        super.onStop()
    }


    public override fun onDestroy() {
        super.onDestroy()
        App().getRefWatcher()?.watch(this)
        if (this.isFinishing) App().getRefWatcher()?.watch(viewModel)
    }
}
