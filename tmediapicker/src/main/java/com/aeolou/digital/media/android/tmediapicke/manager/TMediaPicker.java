package com.aeolou.digital.media.android.tmediapicke.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.callbacks.MediaSelectResultCallbacks;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface TMediaPicker {


    void onCreate(Bundle savedInstanceState);


    /**
     * 跳转相应界面
     *
     * @param pageType
     * @param selectLimit 选择限制，默认多选最多9个,0表示无限制
     */
    void toMediaPage(@NonNull PageType pageType, int selectLimit);

    /**
     * 返回选择的媒体资源
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param callbacks
     */
    void onActivityResult(int requestCode, int resultCode, Intent data, MediaSelectResultCallbacks callbacks);


    enum PageType {
        PHOTO_ALL,//加载所有照片
        PHOTO_ALBUM,//加载相册专辑
        VIDEO_ALL,
        VIDEO_ALBUM,
        AUDIO_ALL,
        AUDIO_ALBUM
    }

}
