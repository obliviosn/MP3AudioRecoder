/**   
*    
* 项目名称：AudioRecoder      
* 创建人：  Vber.T  
* 创建时间  16/9/19 下午7:58   
* 修改人：  Vber.T  
* 修改时间：16/9/19 下午7:58   
* 修改备注： 
* 文件名称: com_android_cmcc_recoder_AudioHelper  
* 类描述：   
* @version    
*    
*/
//
// Created by 杨雯方 on 16/9/19.
//

#ifndef AUDIORECODER_COM_ANDROID_CMCC_RECODER_AUDIOHELPER_H
#define AUDIORECODER_COM_ANDROID_CMCC_RECODER_AUDIOHELPER_H
#include <jni.h>

JNIEXPORT jint JNICALL
        Java_com_android_cmcc_recoder_AudioHelper_nativeEncodeAudioMP3(JNIEnv *env, jobject instance,
                                                                       jshortArray in_, jbyteArray out_,jint inLehgth_);

JNIEXPORT jint JNICALL
        Java_com_android_cmcc_recoder_AudioHelper_nativeInit(JNIEnv *env, jobject instance, jint channel,
                                                       jint sampleRate, jint brate);

JNIEXPORT void JNICALL
        Java_com_android_cmcc_recoder_AudioHelper_nativeDestroy(JNIEnv *env, jobject instance);
#endif //AUDIORECODER_COM_ANDROID_CMCC_RECODER_AUDIOHELPER_H
