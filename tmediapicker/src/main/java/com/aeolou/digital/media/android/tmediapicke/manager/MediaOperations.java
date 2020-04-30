package com.aeolou.digital.media.android.tmediapicke.manager;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.listener.OnMediaContentChangeListener;
import com.aeolou.digital.media.android.tmediapicke.provider.ContextManager;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface MediaOperations {

    //设置加载媒体数据类型，默认为加载照片（可选择文件或专辑）
    void setLoaderMediaType(@NonNull LoaderMediaType loaderMediaType);

    //设置加载媒体数据存储类型，默认为加载所有（本地、外置SD、usb）
    void setLoaderStorageType(@NonNull LoaderStorageType loaderStorageType);

    //加载数据，
    void load();

    //加载指定媒体类型数据
    void load(@NonNull LoaderMediaType loaderMediaType);

    //加载指定专辑媒体数据（桶名即专辑名bucketName通过LoaderMediaType加载照片、视频、音频专辑获取）
    void loadAlbum(@NonNull String bucketName);

    //加载指定专辑指定类型媒体数据
    void loadAlbum(@NonNull String bucketName, @NonNull LoaderMediaType loaderMediaType);

    //设置照片数据回调
    void setPhotoCallbacks(PhotoCallbacks photoCallbacks);

    //设置视频数据回调
    void setVideoCallbacks(VideoCallbacks videoCallbacks);

    //设置音频数据回调
    void setAudioCallbacks(AudioCallbacks audioCallbacks);

    //设置媒体数据库数据变化监听
    void setOnMediaContentChangeListener(OnMediaContentChangeListener listener);

    void clear();
}
