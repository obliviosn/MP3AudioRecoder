package com.android.cmcc.recoder;

/**
 * 项目名称： AudioRecoder
 * 创建人：   Vber.T
 * 创建时间： 16/9/19 下午7:30
 * 修改人：   Vber.T
 * 修改时间： 16/9/19 下午7:30
 * 修改备注：
 * 文件名称： AudioHelper
 * 类描述：
 */
public class AudioHelper {


    static {

        try {
            System.loadLibrary("audio-jni");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置MP3的 channel,采样率,码率等,使用PCM中的值
     *
     * @param channel
     * @param sampleRate
     * @param brate
     */
    public static native int nativeInit(int channel, int sampleRate, int brate);

    /**
     * MP3转码
     *
     * @param in
     * @param out
     * @return
     */
    public static native int nativeEncodeAudioMP3(short[] in, byte[] out, int inLength);

    /**
     * 释放本地资源
     */
    public static native void nativeDestroy();
}
