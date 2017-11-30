package com.toan_itc.core.imageload

import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by Toan.IT on 6/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

interface ImageLoaderListener {

    fun loadImage(url: String, simpleDraweeView: SimpleDraweeView?)

    fun loadController(url: String, simpleDraweeView: SimpleDraweeView?, width: Int, height: Int, controllerListener: ControllerListener<Any>?)

    fun loadController(url: String, simpleDraweeView: SimpleDraweeView?, width: Float, height: Float)

    fun loadHierarchy(url: String, simpleDraweeView: SimpleDraweeView)

}