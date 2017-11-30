package com.nct.xmusicstation.binding

import android.databinding.DataBindingComponent
import android.support.v4.app.Fragment
import com.nct.xmusicstation.binding.FragmentBindingAdapters
import com.toan_itc.core.imageload.ImageLoaderListener

/**
 * Created by Toan.IT on 11/3/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

/**
 * A Data Binding Component implementation for fragments.
 */
class FragmentDataBindingComponent(fragment: Fragment, imageLoaderListener: ImageLoaderListener?) : DataBindingComponent {
    override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
        return fragmentBindingAdapters
    }

    private val fragmentBindingAdapters: FragmentBindingAdapters = FragmentBindingAdapters(fragment, imageLoaderListener)

}
