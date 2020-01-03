package com.aeolou.digital.media.android.tmediapicke.callbacks;

import com.aeolou.digital.media.android.tmediapicke.models.PhotoAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface PhotoCallbacks extends MediaBaseCallbacks {

    //返回所有照片数据
    void onPhotoResult(List<PhotoInfo> photoInfoList);

    //返回相册数据
    void onPhotoAlbumResult(List<PhotoAlbumInfo> photoAlbumInfoList);

}
