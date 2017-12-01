package com.nct.xmusicstation.ui.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.nct.xmusicstation.App
import com.toan_itc.core.base.BaseViewModel
import com.toan_itc.core.base.BindingActivity

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BindingActivity<VM, DB>() {
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
