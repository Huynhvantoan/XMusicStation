package com.nct.xmusicstation.library.snackbar

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by Toan.IT on 11/2/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(lifecycleOwner: LifecycleOwner,
                       snackbarMessageLiveEvent: SingleLiveEvent<Int>, timeLength: Int) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackbar(context.getString(it), timeLength) }
    })
}
