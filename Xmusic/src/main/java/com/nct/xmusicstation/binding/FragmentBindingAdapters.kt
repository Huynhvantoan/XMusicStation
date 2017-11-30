package com.nct.xmusicstation.binding

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.nct.xmusicstation.R
import com.toan_itc.core.imageload.ImageLoaderListener
import com.toan_itc.core.richutils.dimenRes
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapters
@Inject
internal constructor(internal val fragment: Fragment, private val imageLoaderListener: ImageLoaderListener?) {
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: SimpleDraweeView, url: String) {
        imageLoaderListener?.loadController(url, imageView, fragment.dimenRes(R.dimen.image_song_width), fragment.dimenRes(R.dimen.image_song_height))
    }

    @BindingAdapter("imageUrlThumbnail")
    fun bindImageThumbnail(imageView: SimpleDraweeView, url: String) {
        imageLoaderListener?.loadController(url, imageView,fragment.dimenRes(R.dimen.image_song_height),fragment.dimenRes(R.dimen.image_song_height))
    }
}
@BindingAdapter("onClick")
fun setOnClick(view: View, listener: View.OnClickListener) {
    view.setOnClickListener(listener)
}


@BindingAdapter("onLongClick")
fun setOnLongClick(view: View, listener: View.OnLongClickListener) {
    view.setOnLongClickListener(listener)
}


@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}


@BindingAdapter("invisible")
fun setInvisible(view: View, invisible: Boolean) {
    view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}


@BindingAdapter("gone")
fun setGone(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}


@BindingAdapter("imageBitmap")
fun setImageBitmap(imageView: ImageView, bitmap: Bitmap) {
    imageView.setImageBitmap(bitmap)
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("html")
fun html(webView: WebView, html: String) {
    webView.loadDataWithBaseURL(null, html, "text/html", "utf8", null)
}