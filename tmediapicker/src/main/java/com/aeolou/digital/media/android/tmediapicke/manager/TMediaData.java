package com.aeolou.digital.media.android.tmediapicke.manager;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.listener.OnMediaContentChangeListener;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class TMediaData implements MediaOperations {
    private MediaCollection mediaCollection;

    protected TMediaData(MediaCollection mediaCollection) {
        this.mediaCollection = mediaCollection;
    }

    /**
     * 加载数据
     */
    @Override
    public void load() {
        mediaCollection.load();
    }

    @Override
    public void load(@NonNull LoaderMediaType loaderMediaType) {
        mediaCollection.load(loaderMediaType);
    }

    @Override
    public void loadAlbum(@NonNull String bucketName) {
        mediaCollection.loadAlbum(bucketName);
    }

    @Override
    public void loadAlbum(@NonNull String bucketName, @NonNull LoaderMediaType loaderMediaType) {
        mediaCollection.loadAlbum(bucketName, loaderMediaType);
    }

    /**
     * 照片文件加载结果返回
     *
     * @param photoCallbacks
     */
    @Override
    public void setPhotoCallbacks(PhotoCallbacks photoCallbacks) {
        mediaCollection.setPhotoCallbacks(photoCallbacks);
    }

    /**
     * 视频文件加载结果返回
     *
     * @param videoCallbacks
     */
    @Override
    public void setVideoCallbacks(VideoCallbacks videoCallbacks) {
        mediaCollection.setVideoCallbacks(videoCallbacks);
    }


    /**
     * 音频文件加载结果返回
     *
     * @param audioCallbacks
     */
    @Override
    public void setAudioCallbacks(AudioCallbacks audioCallbacks) {
        mediaCollection.setAudioCallbacks(audioCallbacks);

    }

    /**
     * 设置加载文件存储类型
     *
     * @param loaderStorageType
     */
    @Override
    public void setLoaderStorageType(LoaderStorageType loaderStorageType) {
        mediaCollection.setLoaderStorageType(loaderStorageType);
    }

    /**
     * 设置媒体文件数据库内容变化监听
     *
     * @param listener
     */
    @Override
    public void setOnMediaContentChangeListener(OnMediaContentChangeListener listener) {
        mediaCollection.setOnMediaContentChangeListener(listener);
    }


    @Override
    public void clear() {
        mediaCollection.clear();
    }

}
