package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;


import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.BaseAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoAlbumInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoAlbumLoader extends BaseMediaAlbumLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<PhotoAlbumInfo> photoAlbumInfoList;
    private PhotoCallbacks callbacks;
    private Handler handler;
    private LoaderStorageType loaderStorageType;
    private Context context;

    public PhotoAlbumLoader(Context context, LoaderStorageType loaderStorageType, PhotoCallbacks callbacks, Handler handler) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        this.loaderStorageType = loaderStorageType;
        this.callbacks = callbacks;
        this.handler = handler;
        photoAlbumInfoList = new ArrayList<>();

    }


    @Override
    public void run() {
        String selection = getSelectionType(context, loaderStorageType);
        Cursor cursor = contentResolver.query(TConstants.PHOTO_URI, TConstants.PHOTO_ALBUM_PROJECTION,
                selection, null, TConstants.PHOTO_SORT_ORDER);
        if (cursor == null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callbacks != null) callbacks.onError(new Throwable("cursor is null !"));
                }
            });
            return;
        }
        ArrayList<PhotoAlbumInfo> temp = new ArrayList<>(cursor.getCount());
        ArrayList<String> albumSet = new ArrayList<>();
        if (cursor.moveToLast()) {
            PhotoAlbumInfo photoAlbumInfo;
            File file;
            String bucketName;
            do {
                bucketName = cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_ALBUM_PROJECTION[1]));
                file = new File(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_ALBUM_PROJECTION[2])));
                if (!file.exists()) continue;
                if (!albumSet.contains(bucketName)) {
                    photoAlbumInfo = new PhotoAlbumInfo();
                    photoAlbumInfo.setBucketId(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_ALBUM_PROJECTION[0])));
                    photoAlbumInfo.setBucketName(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_ALBUM_PROJECTION[1])));
                    photoAlbumInfo.setPreviewPath(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_ALBUM_PROJECTION[2])));
                    temp.add(photoAlbumInfo);
                    albumSet.add(bucketName);
                } else {
                    photoAlbumInfo = temp.get(albumSet.indexOf(bucketName));
                    photoAlbumInfo.setCount(photoAlbumInfo.getCount() + 1);
                }
                photoAlbumInfo.setType(BaseAlbumInfo.AUDIO);

            } while (cursor.moveToPrevious());
        }
        cursor.close();
        photoAlbumInfoList.clear();
        photoAlbumInfoList.addAll(temp);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callbacks != null) callbacks.onPhotoAlbumResult(photoAlbumInfoList,loaderStorageType);
            }
        });
    }
}
