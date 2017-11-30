package com.nct.xmusicstation.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import com.nct.xmusicstation.databinding.LoginActivityBinding
import com.nct.xmusicstation.ui.base.viewmodel.ViewModelFactory
import com.toan_itc.core.base.BaseBindingActivity

class LoginActivity : BaseBindingActivity<LoginViewModel, LoginActivityBinding>() , LoginView {
    override fun onClick() {
        viewModel.updateMessage()
    }

    override fun onLongClick(): Boolean {
        viewModel.updateMessage()
        return true
    }

    override fun setupViewModel(): LoginViewModel {
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        lifecycle.addObserver(viewModel)
        return viewModel
    }

    override fun inflateBindingLayout(inflater: LayoutInflater): LoginActivityBinding {
       return LoginActivityBinding.inflate(inflater)
    }


}
