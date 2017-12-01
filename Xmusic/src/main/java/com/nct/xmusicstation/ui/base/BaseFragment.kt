package com.nct.xmusicstation.ui.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.nct.xmusicstation.App
import com.nct.xmusicstation.library.snackbar.SnackbarEvent
import com.orhanobut.logger.Logger
import com.toan_itc.core.base.BaseViewModel
import com.toan_itc.core.base.BindingFragment

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BindingFragment<VM, DB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("onCreate:" + this.javaClass.simpleName)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
    }

    override fun onDestroyView() {
        Logger.v("onDestroyView:" + this.javaClass.simpleName)
        super.onDestroyView()
    }


    override fun onDestroy() {
        Logger.v("onDestroy:" + this.javaClass.simpleName)
        super.onDestroy()
        App().getRefWatcher()?.watch(this)
        if (activity!!.isFinishing) App().getRefWatcher()?.watch(viewModel)
    }


    fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun setupObservers() {
        //viewModel.observeEvent<SnackbarEvent>(this, SnackbarEvent::class.java, { snackbarEvent -> showSnackbar(snackbarEvent.message) })
    }
}
