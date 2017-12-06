package com.nct.xmusicstation.utils

import android.text.TextUtils.isEmpty
import com.nct.xmusicstation.App
import com.orhanobut.logger.Logger
import java.io.File

/**
 * Created by Toan.IT on 11/04/17.
 * Email:Huynhvantoan.itc@gmail.com
 */


fun getThemePath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Theme"
}

fun getSongPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Song"
}

fun getLyricPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Lyric"
}

fun getSongPathUpload(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "SongUpload"
}

fun getSongPathUpdate(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Update"
}

fun getMvPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Mv"
}

fun getImageUploadPath(imageUpload: String): String {
    return getMvPath() + File.separator + imageUpload.substring(imageUpload.lastIndexOf(File.separator) + 1)
}

fun getRecordedPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Recorded"
}

fun getFolderPath(): String? {
    return App().getInstance()?.getExternalFilesDir(null)?.path
}

fun getIntroVideoPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Intro_kct.mp4"
}

fun getImagePath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "Image"
}

fun getImageIntroPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "KCTIntro.jpg"
}

fun getImageSplashPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "KCTSplash.jpg"
}

fun getImageWelcomePath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "KCTWelcome.jpg"
}

fun getImageMerchantPath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path + File.separator + "KCTMerchant.jpg"
}

fun getQRCodeFilePath(): String {
    return App().getInstance()?.getExternalFilesDir(null)?.path +
            File.separator + "Ultrasound" + File.separator + "QrCode.png"
}

fun pathLocalBeat(linkBeat: String): String {
    return if (linkBeat.contains(Constants.FOLDER_MIDI))
        Constants.PATH_HDD + Constants.FOLDER_MIDI_EN + File.separator + linkBeat.substring(linkBeat.indexOf(Constants.FOLDER_MIDI_EN) + 10)
    else
        Constants.PATH_HDD + Constants.FOLDER_BEAT + File.separator + linkBeat.substring(linkBeat.indexOf(Constants.FOLDER_BEAT) + 10)
}

fun pathLocalBeat(linkBeat: String, songID: Int): String {
    if (linkBeat.contains(Constants.FOLDER_AAC))
        return getSongPath() + File.separator + songID + Constants.SONG_ACC_BEAT
    else if (linkBeat.contains(Constants.FOLDER_MIDI))
        return getSongPath() + File.separator + songID + Constants.SONG_MIDI_BEAT
    return getSongPath() + File.separator + songID + Constants.SONG_BEAT
}

fun pathLocalMp3(linkMp3: String): String {
    return if (isEmpty(linkMp3)) "" else Constants.PATH_HDD + Constants.FOLDER_MP3 + File.separator + linkMp3.substring(linkMp3.indexOf(Constants.FOLDER_MP3) + 4)
}

fun pathLocalMp3(linkMp3: String, songID: Int): String {
    if (isEmpty(linkMp3))
        return ""
    return if (linkMp3.contains(Constants.FOLDER_AAC))
        getSongPath() + File.separator + songID + Constants.SONG_AAC
    else
        getSongPath() + File.separator + songID + Constants.SONG_MP3
}

fun pathLocalMV(linkMV: String): String {
    return Constants.PATH_HDD + Constants.FOLDER_MV + File.separator + linkMV.substring(linkMV.indexOf(Constants.FOLDER_MV) + 3)
}

fun pathLocalMV(songID: Int, isMVUpload: String): String {
    return getMvPath() + File.separator + songID + isMVUpload
}

fun pathLocalLyric(linkLyric: String): String {
    return if (!isEmpty(linkLyric))
        Constants.PATH_HDD + Constants.FOLDER_LYRIC + File.separator + linkLyric.substring(linkLyric.indexOf(Constants.FOLDER_LYRIC) + 11)
    else
        ""
}

fun pathLocalLyric(songID: Int): String {
    return getLyricPath() + File.separator + songID + Constants.LYRIC_XML
}

fun pathImageUpload(path: String): String {
    return getImageUploadPath(path)
}

fun pathLocalImage(linkImage: String): String {
    return Constants.PATH_HDD + Constants.FOLDER_IMAGE + File.separator + linkImage.substring(linkImage.indexOf(Constants.FOLDER_IMAGE) + 6)
}

fun pathLocalImageSongSearch(linkImage: String): String {
    return pathLocalImage(linkImage)
}

fun pathLocalImageSinger(linkImage: String): String {
    return pathLocalImage(linkImage)
}

fun pathLocalImageFull(linkImage: String): String {
    val urlImage = Constants.PATH_HDD + Constants.FOLDER_IMAGE + File.separator + linkImage.substring(linkImage.indexOf(Constants.FOLDER_IMAGE) + 6)
    return urlImage.replace("_460_230", "")
}

fun isLocalBeat(linkBeat: String): Boolean {
    Logger.d("pathBeat=" + isFileExists(pathLocalBeat(linkBeat)) + "\npathLocalBeat=" + pathLocalBeat(linkBeat))
    return isFileExists(pathLocalBeat(linkBeat))
}

fun isLocalBeat(link: String, songID: Int): Boolean {
    //Logger.d("isLocalBeatCache="+isFileExists(pathLocalBeat(songID)));
    return isFileExists(pathLocalBeat(link, songID))
}

fun isLocalMp3(linkMp3: String): Boolean {
    if (linkMp3.isEmpty())
        return false
    Logger.d("isFileExists=" + isFileExists(pathLocalMp3(linkMp3)) + "\npathMp3=" + pathLocalMp3(linkMp3))
    return isFileExists(pathLocalMp3(linkMp3))
}

fun isLocalMp3(link: String, songID: Int): Boolean {
    return if (link.isEmpty()) false else isFileExists(pathLocalMp3(link, songID))
    //Logger.d("isLocalBeatCache="+isFileExists(pathLocalMp3(songID)));
}

fun isLocalMv(linkMV: String): Boolean {
    if (linkMV.isEmpty())
        return false
    Logger.d("isFileExists=" + isFileExists(pathLocalMV(linkMV)) + "\npathMV=" + pathLocalMV(linkMV))
    return isFileExists(pathLocalMV(linkMV))
}

fun isLocalMv(songID: Int, isMVUpload: String): Boolean {
    Logger.d(isFileExists(getMvPath() + File.separator + songID + isMVUpload))
    return isFileExists(getMvPath() + File.separator + songID + isMVUpload)
}

fun isLocalLyric(linkLyric: String): Boolean {
    if (linkLyric.isEmpty())
        return false
    //Logger.d(isFileExists(pathLocalLyric(linkLyric))+"\n pathLocalLyric="+pathLocalLyric(linkLyric));
    return isFileExists(pathLocalLyric(linkLyric))
}

fun isLocalLyric(songID: Int): Boolean {
    return isFileExists(getLyricPath() + File.separator + songID + Constants.LYRIC_XML)
}

fun isImageUpload(path: String): Boolean {
    return isFileExists(pathImageUpload(path))
}

fun isLocalImage(linkImage: String): Boolean {
    //Logger.d(isFileExists(pathLocalImage(linkImage)) + "\npathIMAGE=" + pathLocalImage(linkImage))
    return isFileExists(pathLocalImage(linkImage))
}

fun isLocalImageSinger(linkImage: String): Boolean {
    //Logger.d(isFileExists(pathLocalImage(linkImage))+"\npathIMAGE="+pathLocalImage(linkImage));
    return isFileExists(pathLocalImageSinger(linkImage))
}

fun isLocalImageFull(linkImage: String): Boolean {
    //Logger.d(isFileExists(pathLocalImageFull(linkImage))+"\npathIMAGE="+pathLocalImageFull(linkImage));
    return isFileExists(pathLocalImageFull(linkImage))
}
