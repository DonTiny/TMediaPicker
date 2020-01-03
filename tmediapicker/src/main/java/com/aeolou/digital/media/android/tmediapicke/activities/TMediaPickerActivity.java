package com.aeolou.digital.media.android.tmediapicke.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aeolou.digital.media.android.tmediapicke.callbacks.MediaSelectResultCallbacks;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaInvocationHandler;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaPicker;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaPickerImpl;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;
import com.aeolou.digital.media.android.tmediapicke.utils.GsonUtil;
import com.aeolou.digital.media.android.tmediapicke.utils.LogUtils;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class TMediaPickerActivity extends AppCompatActivity implements MediaSelectResultCallbacks {
    private TMediaPicker tMediaPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTMediaPicker().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    public TMediaPicker getTMediaPicker() {
        if (tMediaPicker == null) {
            tMediaPicker = (TMediaPicker) TMediaInvocationHandler.bind(new TMediaPickerImpl(this));
        }
        return tMediaPicker;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTMediaPicker().onActivityResult(requestCode, resultCode, data, this);

    }


    @Override
    public void onPhotoResult(List<PhotoInfo> photoInfoList) {
        LogUtils.i("Return photo data" + GsonUtil.gsonString(photoInfoList));

    }

    @Override
    public void onVideoResult(List<VideoInfo> videoInfoList) {
        LogUtils.i("Return video data" + GsonUtil.gsonString(videoInfoList));

    }

    @Override
    public void onAudioResult(List<AudioInfo> audioInfoList) {
        LogUtils.i("Return audio data" + GsonUtil.gsonString(audioInfoList));

    }
}
