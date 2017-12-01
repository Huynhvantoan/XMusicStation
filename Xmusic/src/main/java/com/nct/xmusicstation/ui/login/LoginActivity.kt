package com.nct.xmusicstation.ui.login

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import com.nct.xmusicstation.R
import com.nct.xmusicstation.databinding.LoginActivityBinding
import com.nct.xmusicstation.ui.base.BaseActivity
import com.toan_itc.core.base.BaseBindingActivity
import com.toan_itc.core.base.BindingActivity

class LoginActivity : BaseBindingActivity<LoginViewModel, LoginActivityBinding>() , LoginView {

    override fun inflateBindingLayout(inflater: LayoutInflater): LoginActivityBinding {
        return LoginActivityBinding.inflate(inflater)
    }

    override fun setupViewModel(): LoginViewModel {
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        lifecycle.addObserver(viewModel)
        return viewModel
    }

    /*override fun getLayoutRes(): Int {
        return R.layout.login_activity
    }

    override fun getViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun addObserver(): LifecycleObserver {
        return viewModel
    }*/

    override fun onClick() {
        viewModel.updateMessage()
    }

    override fun onLongClick(): Boolean {
        viewModel.updateMessage()
        return true
    }
}
