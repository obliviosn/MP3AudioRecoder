
LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE    := libmp3lame
LOCAL_SRC_FILES := libs/libmp3lame.a

include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE     := audio-jni
LOCAL_CFLAGS     += -Wall

LOCAL_SRC_FILES := com_android_cmcc_recoder_AudioHelper.c


LOCAL_STATIC_LIBRARIES := libmp3lame


include $(BUILD_SHARED_LIBRARY)

