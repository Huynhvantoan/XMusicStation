package com.toan_itc.core.base

import android.arch.lifecycle.ViewModelProvider
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater

import com.toan_itc.core.BR
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by Toan.IT on 12/1/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

abstract class BaseBindingActivity<T : BaseViewModel, B : ViewDataBinding> : DaggerAppCompatActivity(), BaseView {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: T
        private set
    lateinit var binding: B
        private set

    abstract fun setupViewModel(): T
    abstract fun inflateBindingLayout(inflater: LayoutInflater): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = setupViewModel()
        binding = setupBinding(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupBinding(inflater: LayoutInflater): B {
        val binding = inflateBindingLayout(inflater)
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.viewModel, viewModel)
        return binding
    }
}