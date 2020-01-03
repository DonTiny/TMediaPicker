package com.aeolou.digital.media.android.tmediapicke.manager;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.loader.LoaderMediaType;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface MediaOperations {

    void load();

    void load(@NonNull LoaderMediaType loaderMediaType);

    void loadAlbum(@NonNull String bucketName);

    void loadAlbum(@NonNull String bucketName, @NonNull LoaderMediaType loaderMediaType);

    void setPhotoCallbacks(PhotoCallbacks photoCallbacks);

    void setVideoCallbacks(VideoCallbacks videoCallbacks);

    void setAudioCallbacks(AudioCallbacks audioCallbacks);

    void clear();
}
