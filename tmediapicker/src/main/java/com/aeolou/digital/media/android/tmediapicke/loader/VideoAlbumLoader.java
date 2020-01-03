package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;

import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.VideoAlbumInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class VideoAlbumLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<VideoAlbumInfo> videoAlbumInfoList;
    private VideoCallbacks callbacks;
    private Handler handler;

    public VideoAlbumLoader(ContentResolver contentResolver, VideoCallbacks callbacks, Handler handler) {
        this.contentResolver = contentResolver;
        this.callbacks = callbacks;
        this.handler = handler;
        videoAlbumInfoList = new ArrayList<>();

    }


    private Uri getVideoUri(String video_id) {
        return Uri.withAppendedPath(TConstants.VIDEO_URI, video_id);
    }

    @Override
    public void run() {
        Cursor cursor = contentResolver.query(TConstants.VIDEO_URI, TConstants.VIDEO_ALBUM_PROJECTION,
                null, null, TConstants.VIDEO_SORT_ORDER);
        if (cursor == null) {
            if (callbacks != null) {
                callbacks.onError(new Throwable("cursor is null !"));
            }
            return;
        }
        videoAlbumInfoList.clear();
        ArrayList<VideoAlbumInfo> temp = new ArrayList<>(cursor.getCount());
        ArrayList<String> albumSet = new ArrayList<>();
        if (cursor.moveToLast()) {
            VideoAlbumInfo videoAlbumInfo;
            File file;
            String bucketName;
            String id;
            do {
                bucketName = cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_ALBUM_PROJECTION[2]));
                id = cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_ALBUM_PROJECTION[0]));
                file = new File(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_ALBUM_PROJECTION[3])));
                if (!albumSet.contains(bucketName) && file.exists()) {
                    videoAlbumInfo = new VideoAlbumInfo();
                    videoAlbumInfo.setBucketId(cursor.getString(cursor.getColumnIndex(TConstants.VIDEO_ALBUM_PROJECTION[1])));
                    videoAlbumInfo.setBucketName(bucketName);
                    videoAlbumInfo.setPreviewUri(getVideoUri(id).toString());
                    temp.add(videoAlbumInfo);
                    albumSet.add(bucketName);
                } else {
                    videoAlbumInfo = temp.get(albumSet.indexOf(bucketName));
                    videoAlbumInfo.setCount(videoAlbumInfo.getCount() + 1);
                }

            } while (cursor.moveToPrevious());
        }
        cursor.close();
        videoAlbumInfoList.clear();
        videoAlbumInfoList.addAll(temp);
        handler.post(new Runnable() {
            @Override
            public void run() {
                callbacks.onVideoAlbumResult(videoAlbumInfoList);
            }
        });
    }
}
