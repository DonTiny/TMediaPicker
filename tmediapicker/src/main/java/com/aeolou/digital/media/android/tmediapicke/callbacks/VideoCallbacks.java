package com.aeolou.digital.media.android.tmediapicke.callbacks;

import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.models.VideoAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface VideoCallbacks extends MediaBaseCallbacks {

    //返回所有视频数据
    void onVideoResult(List<VideoInfo> videoInfoList, LoaderStorageType loaderStorageType);

    //返回视频文件专辑
    void onVideoAlbumResult(List<VideoAlbumInfo> videoAlbumInfoList,LoaderStorageType loaderStorageType);
}
