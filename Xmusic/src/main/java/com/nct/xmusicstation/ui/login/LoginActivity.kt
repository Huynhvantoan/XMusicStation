package com.nct.xmusicstation.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import com.nct.xmusicstation.R
import com.nct.xmusicstation.databinding.LoginActivityBinding
import com.toan_itc.core.base.BindingActivity

class LoginActivity : BindingActivity<LoginViewModel, LoginActivityBinding>() , LoginView {
    override fun getLayoutRes(): Int {
        return R.layout.login_activity
    }

    override fun getViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onClick() {
        viewModel.updateMessage()
    }

    override fun onLongClick(): Boolean {
        viewModel.updateMessage()
        return true
    }
}
