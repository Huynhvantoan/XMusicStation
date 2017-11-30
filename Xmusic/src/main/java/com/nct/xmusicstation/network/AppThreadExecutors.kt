package com.nct.xmusicstation.network

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Toan.IT on 11/2/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

val IO_EXECUTOR = Executors.newSingleThreadExecutor()
val NETWORK_EXECUTOR = Executors.newFixedThreadPool(3)
val MAIN_EXECUTOR = MainThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}

fun networkThread(f: () -> Unit) {
    NETWORK_EXECUTOR.execute(f)
}

fun mainThread(f: () -> Unit) {
    MAIN_EXECUTOR.execute(f)
}

class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}
