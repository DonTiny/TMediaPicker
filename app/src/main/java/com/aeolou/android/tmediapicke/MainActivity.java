package com.aeolou.android.tmediapicke;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aeolou.digital.media.android.tmediapicke.activities.TMediaPickerActivity;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaPicker;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;

import java.util.List;


public class MainActivity extends TMediaPickerActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_photo:
                getTMediaPicker().toMediaPage(TMediaPicker.PageType.PHOTO_ALL, 9);
                break;
            case R.id.btn_select_video:
                getTMediaPicker().toMediaPage(TMediaPicker.PageType.VIDEO_ALL, 0);
                break;
            case R.id.btn_select_music:
                getTMediaPicker().toMediaPage(TMediaPicker.PageType.AUDIO_ALL, 8);
                break;
            case R.id.btn_select_photo_album:
                getTMediaPicker().toMediaPage(TMediaPicker.PageType.PHOTO_ALBUM, 0);
                break;
            case R.id.btn_select_video_album:
                getTMediaPicker().toMediaPage(TMediaPicker.PageType.VIDEO_ALBUM, 0);
                break;
            case R.id.btn_select_audio_album:
                getTMediaPicker().toMediaPage(TMediaPicker.PageType.AUDIO_ALBUM, 9);
                break;
        }
    }
    private Button mBtn_select_photo;
    private Button mBtn_select_video;
    private Button mBtn_select_music;
    private Button mBtn_select_photo_album;
    private Button mBtn_select_video_album;
    private Button mBtn_select_audio_album;
    private TextView mTv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        initListener();
        initData();
    }

    private void init() {

    }

    protected void initView() {
        mBtn_select_photo = (Button) findViewById(R.id.btn_select_photo);
        mBtn_select_video = (Button) findViewById(R.id.btn_select_video);
        mBtn_select_music = (Button) findViewById(R.id.btn_select_music);
        mBtn_select_photo_album = (Button) findViewById(R.id.btn_select_photo_album);
        mBtn_select_video_album = (Button) findViewById(R.id.btn_select_video_album);
        mBtn_select_audio_album = (Button) findViewById(R.id.btn_select_audio_album);
        mTv_data = (TextView) findViewById(R.id.tv_data);


    }

    protected void initListener() {
        mBtn_select_photo.setOnClickListener(this);
        mBtn_select_video.setOnClickListener(this);
        mBtn_select_music.setOnClickListener(this);
        mBtn_select_photo_album.setOnClickListener(this);
        mBtn_select_video_album.setOnClickListener(this);
        mBtn_select_audio_album.setOnClickListener(this);
    }

    protected void initData() {

    }



    String dataStr = "";

    @Override
    public void onPhotoResult(List<PhotoInfo> photoInfoList) {
        super.onPhotoResult(photoInfoList);
        dataStr = GsonUtil.gsonString(photoInfoList);
        LogUtils.i("返回图片数据" + dataStr);
        mTv_data.setText(dataStr);

    }

    @Override
    public void onVideoResult(List<VideoInfo> videoInfoList) {
        super.onVideoResult(videoInfoList);
        dataStr = GsonUtil.gsonString(videoInfoList);
        LogUtils.i("返回视频数据" + dataStr);
        mTv_data.setText(dataStr);

    }

    @Override
    public void onAudioResult(List<AudioInfo> audioInfoList) {
        super.onAudioResult(audioInfoList);
        dataStr = GsonUtil.gsonString(audioInfoList);
        LogUtils.i("返回音乐数据" + dataStr);
        mTv_data.setText(dataStr);
    }

}
