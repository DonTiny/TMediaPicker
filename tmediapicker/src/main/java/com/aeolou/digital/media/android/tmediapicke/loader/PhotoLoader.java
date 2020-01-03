package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<PhotoInfo> photoInfoList;
    private PhotoCallbacks callbacks;
    private String bucketName;
    private Handler handler;

    public PhotoLoader(ContentResolver contentResolver, String bucketName, PhotoCallbacks callbacks, Handler handler) {
        this.contentResolver = contentResolver;
        this.callbacks = callbacks;
        this.bucketName = bucketName;
        this.handler = handler;
        photoInfoList = new ArrayList<>();

    }

    @Override
    public void run() {
        String selection;
        String[] selectionArgs;
        if (TextUtils.isEmpty(bucketName)) {
            selection = null;
            selectionArgs = null;
        } else {
            selection = TConstants.PHOTO_PROJECTION[5] + " =?";
            selectionArgs = new String[]{bucketName};
        }
        Cursor cursor = contentResolver.query(TConstants.PHOTO_URI, TConstants.PHOTO_PROJECTION, selection, selectionArgs, TConstants.PHOTO_SORT_ORDER);
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
        photoInfoList.clear();
        if (cursor.moveToLast()) {
            File file;
            PhotoInfo photoInfo;
            do {
                file = new File(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[3])));
                photoInfo = new PhotoInfo();
                if (file.exists()) {
                    photoInfo.setId(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[0])));
                    photoInfo.setTitle(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[1])));
                    photoInfo.setDisplayName(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[2])));
                    photoInfo.setData(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[3])));
                    photoInfo.setSize(cursor.getLong(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[4])));
                    photoInfo.setBucketDisplayName(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[5])));
                    photoInfo.setBucketId(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[6])));
                    photoInfo.setDateAdded(cursor.getLong(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[7])));
                    photoInfo.setDateModified(cursor.getLong(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[8])));
                    photoInfo.setDateTaken(cursor.getLong(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[9])));
                    photoInfo.setHeight(cursor.getInt(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[10])));
                    photoInfo.setWidth(cursor.getInt(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[11])));
                    photoInfo.setMimeType(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[12])));
                    photoInfo.setDescription(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[13])));
                    photoInfo.setMiniThumbMagic(cursor.getString(cursor.getColumnIndex(TConstants.PHOTO_PROJECTION[14])));
                    photoInfoList.add(photoInfo);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        handler.post(new Runnable() {
            @Override
            public void run() {
                callbacks.onPhotoResult(photoInfoList);
            }
        });
    }
}
