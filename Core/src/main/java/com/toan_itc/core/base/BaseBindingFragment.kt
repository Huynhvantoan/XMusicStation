package com.toan_itc.core.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.toan_itc.core.BR

/**
 * Created by Toan.IT on 11/30/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
abstract class BaseBindingFragment<T : BaseViewModel, B : ViewDataBinding> : CoreFragment<T>() {
    lateinit var binding: B
        private set


    abstract fun inflateBindingLayout(inflater: LayoutInflater): B


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = setupBinding(inflater)
        return binding.root
    }


    private fun setupBinding(inflater: LayoutInflater): B {
        val binding = inflateBindingLayout(inflater)
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.viewModel, viewModel)
        return binding
    }
}
