#include <jni.h>
#include "lame.h"
#include <malloc.h>

#define BUFFER_SIZE 4096
lame_t lame;


JNIEXPORT jint JNICALL
Java_com_android_cmcc_recoder_AudioHelper_nativeEncodeAudioMP3(JNIEnv *env, jobject instance,
                                                               jshortArray in_, jbyteArray out_,
                                                               jint inLehgth_) {
    jshort *in = (*env)->GetShortArrayElements(env, in_, JNI_OK);
    jint inSize = (*env)->GetArrayLength(env, in_);

    // TODO  转换只需要这一个函数
    unsigned char *out = (unsigned char *) calloc(inLehgth_, sizeof(unsigned char));
    int wLength = lame_encode_buffer(lame, in, in, inSize, out, inLehgth_);

    if (wLength >= 0) {
        (*env)->SetByteArrayRegion(env, out_, 0, wLength, out);
    }

//    free(out);
    (*env)->ReleaseShortArrayElements(env, in_, in, JNI_OK);
    return  wLength;
}

JNIEXPORT jint JNICALL
Java_com_android_cmcc_recoder_AudioHelper_nativeInit(JNIEnv *env, jobject instance, jint channel,
                                                     jint sampleRate, jint brate) {

    // TODO
//    lame_set_num_channels(gfp,2);/* 默认也是2 */
//    lame_set_in_samplerate(gfp,44100);  /* 采样率 */
//    lame_set_brate(gfp,128);     /* 比特率 */
//    lame_set_mode(gfp,1);    /* mode = 0,1,2,3 = stereo,jstereo,dualchannel(not supported),mono default */
//    lame_set_quality(gfp,2);     /* 2=high  5 = medium  7=low */
//
    lame = lame_init();
    lame_set_num_channels(lame, channel);
    lame_set_in_samplerate(lame, sampleRate);
    lame_set_brate(lame, brate);
    lame_set_mode(lame, 1);
    lame_set_num_samples(lame,2);
    lame_set_quality(lame, 1);//设置音质
    return lame_init_params(lame);

}

JNIEXPORT void JNICALL
Java_com_android_cmcc_recoder_AudioHelper_nativeDestroy(JNIEnv *env, jobject instance) {

    // TODO
    lame_close(lame);

}