package com.aeolou.digital.media.android.tmediapicke.callbacks;

import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.models.AudioAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface AudioCallbacks extends MediaBaseCallbacks {
    //返回所有音乐数据
    void onAudioResult(List<AudioInfo> audioInfoList, LoaderStorageType loaderStorageType);

    //返回音乐专辑信息
    void onAudioAlbumResult(List<AudioAlbumInfo> audioAlbumInfoList, LoaderStorageType loaderStorageType);

}