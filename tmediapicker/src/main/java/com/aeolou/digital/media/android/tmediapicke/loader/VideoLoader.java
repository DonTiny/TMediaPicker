package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;

import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.BaseMediaInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class VideoLoader extends BaseMediaLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<VideoInfo> videoInfoList;
    private VideoCallbacks callbacks;
    private String bucketName;
    private Handler handler;
    private LoaderStorageType loaderStorageType;
    private Context context;

    public VideoLoader(Context context, LoaderStorageType loaderStorageType, String bucketName, VideoCallbacks callbacks, Handler handler) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        this.loaderStorageType = loaderStorageType;
        this.callbacks = callbacks;
        this.bucketName = bucketName;
        this.handler = handler;
        videoInfoList = new ArrayList<>();

    }

    @Override
    public void run() {
        String selection;
        String[] selectionArgs;
        if (TextUtils.isEmpty(bucketName)) {
            selection = getSelectionType(context, loaderStorageType);
            selectionArgs = null;
        } else {
            selection = TConstants.VIDEO_PROJECTION[7] + " LIKE ? AND " + getSelectionType(context, loaderStorageType);
            selectionArgs = new String[]{bucketName};
        }
        Cursor cursor = contentResolver.query(TConstants.VIDEO_URI, TConstants.VIDEO_PROJECTION, selection, selectionArgs, TConstants.VIDEO_SORT_ORDER);

        if (cursor == null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callbacks != null) callbacks.onError(new Throwable("cursor is null !"));
                }
            });
            return;
        }
        videoInfoList.clear();
        if (cursor.moveToLast()) {
            File file;
            VideoInfo videoInfo;
            do {
                file = new File(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[5])));
                videoInfo = new VideoInfo();
                if (file.exists()) {
                    videoInfo.setId(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[0])));
                    videoInfo.setTitle(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[1])));
                    videoInfo.setDisplayName(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[2])));
                    videoInfo.setArtist(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[3])));
                    videoInfo.setDuration(cursor.getLong(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[4])));
                    videoInfo.setData(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[5])));
                    videoInfo.setSize(cursor.getLong(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[6])));
                    videoInfo.setBucketDisplayName(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[7])));
                    videoInfo.setBucketId(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[8])));
                    videoInfo.setBookMark(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[9])));
                    videoInfo.setDateAdded(cursor.getLong(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[10])));
                    videoInfo.setDateModified(cursor.getLong(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[11])));
                    videoInfo.setDateTaken(cursor.getLong(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[12])));
                    videoInfo.setHeight(cursor.getInt(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[13])));
                    videoInfo.setWidth(cursor.getInt(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[14])));
                    videoInfo.setMimeType(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[15])));
                    videoInfo.setDescription(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[16])));
                    videoInfo.setMiniThumbMagic(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[17])));
                    videoInfo.setThumbnailsData(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_PROJECTION[18])));
                    videoInfo.setType(BaseMediaInfo.VIDEO);
                    videoInfoList.add(videoInfo);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callbacks != null) callbacks.onVideoResult(videoInfoList, loaderStorageType);
            }
        });
    }
}
