package com.nct.xmusicstation.utils

import java.io.File

/**
 * Created by Toan.IT on 11/1/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
fun createDirLocal(path: String): String {
    return path.substring(0, path.lastIndexOf(File.separator) + 1)
}

fun isFileExists(filePath: String): Boolean {
    return isFileExists(getFileByPath(filePath))
}

fun isFileExists(file: File?): Boolean {
    return file != null && file.exists()
}

fun getFileByPath(filePath: String): File? {
    return if (isSpace(filePath)) null else File(filePath)
}

private fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}