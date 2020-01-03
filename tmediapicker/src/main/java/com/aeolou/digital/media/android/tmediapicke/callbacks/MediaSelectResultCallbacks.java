package com.aeolou.digital.media.android.tmediapicke.callbacks;

import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface MediaSelectResultCallbacks {

    void onPhotoResult(List<PhotoInfo> photoInfoList);

    void onVideoResult(List<VideoInfo> videoInfoList);

    void onAudioResult(List<AudioInfo> audioInfoList);

}
