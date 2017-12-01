package com.toan_itc.core.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toan_itc.core.BR
import dagger.android.support.DaggerFragment

/**
 * Created by Toan.IT on 12/1/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
abstract class BaseBindingFragment<BV : BaseViewModel, DB : ViewDataBinding> : DaggerFragment() {
    lateinit var binding: DB
        private set
    lateinit var viewModel: BV
        private set

    abstract fun setupViewModel(): BV
    abstract fun inflateBindingLayout(inflater: LayoutInflater): DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = setupViewModel()
        binding = setupBinding(inflater)
        return binding.root
    }


    private fun setupBinding(inflater: LayoutInflater): DB {
        val binding = inflateBindingLayout(inflater)
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.viewModel, viewModel)
        return binding
    }
}