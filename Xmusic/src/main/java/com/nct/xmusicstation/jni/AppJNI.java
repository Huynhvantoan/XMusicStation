
package com.nct.xmusicstation.jni;

/**
 * Created by Toan.IT on 3/27/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

public class AppJNI {
    static {
        System.loadLibrary("SuperpoweredExample");
    }
    public static native void SuperpoweredExample(int samplerate, int buffersize, String[] paraPath);
    public static native void setPosition(int milliseconds);
    public static native void onPlayPause(boolean play);
    public static native void onVolume(float volume);
    public static native double detectFileBPM(String path);
    public static native double getPosition();
    public static native double getPositionMs();
    public static native double getDuration();
    public static native double getDurationMs();
    public static native boolean isPlaying();
    public static native boolean isSongCompleted();
    public static native boolean isInit();
    public static native String getString(int key);
    public static native String getMD5(String timestamp, String deviceId);
}
