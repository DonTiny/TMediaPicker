package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;


import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoAlbumInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoAlbumLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<PhotoAlbumInfo> photoAlbumInfoList;
    private PhotoCallbacks callbacks;
    private Handler handler;

    public PhotoAlbumLoader(ContentResolver contentResolver, PhotoCallbacks callbacks, Handler handler) {
        this.contentResolver = contentResolver;
        this.callbacks = callbacks;
        this.handler = handler;
        photoAlbumInfoList = new ArrayList<>();

    }


    @Override
    public void run() {
        Cursor cursor = contentResolver.query(TConstants.PHOTO_URI, TConstants.PHOTO_ALBUM_PROJECTION,
                null, null, TConstants.PHOTO_SORT_ORDER);
        if (cursor == null) {
            if (callbacks != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callbacks.onError(new Throwable("cursor is null !"));
                    }
                });
            }
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
                if (!albumSet.contains(bucketName) && file.exists()) {
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

            } while (cursor.moveToPrevious());
        }
        cursor.close();
        photoAlbumInfoList.clear();
        photoAlbumInfoList.addAll(temp);
        handler.post(new Runnable() {
            @Override
            public void run() {
                callbacks.onPhotoAlbumResult(photoAlbumInfoList);
            }
        });
    }
}
