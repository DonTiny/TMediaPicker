package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.AudioAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.BaseAlbumInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioAlbumLoader extends BaseMediaAlbumLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<AudioAlbumInfo> audioAlbumInfoList;
    private AudioCallbacks callbacks;
    private Handler handler;
    private LoaderStorageType loaderStorageType;
    private Context context;

    public AudioAlbumLoader(Context context, LoaderStorageType loaderStorageType, AudioCallbacks callbacks, Handler handler) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        this.loaderStorageType = loaderStorageType;
        this.callbacks = callbacks;
        this.handler = handler;
        audioAlbumInfoList = new ArrayList<>();
    }


    private String tmp;

    private String getBucketName(String data, String displayDame) {
        tmp = data.replace((File.separator + displayDame), "");
        return tmp.substring(tmp.lastIndexOf(File.separator) + 1);
    }


    @Override
    public void run() {
        String selection = getSelectionType(context, loaderStorageType);
        Cursor cursor = contentResolver.query(TConstants.AUDIO_URI, TConstants.AUDIO_ALBUM_PROJECTION,
                selection, null, TConstants.AUDIO_SORT_ORDER);
        if (cursor == null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callbacks != null) callbacks.onError(new Throwable("cursor is null !"));
                }
            });
            return;
        }
        audioAlbumInfoList.clear();
        ArrayList<AudioAlbumInfo> temp = new ArrayList<>(cursor.getCount());
        ArrayList<String> albumSet = new ArrayList<>();
        if (cursor.moveToLast()) {
            AudioAlbumInfo audioAlbumInfo;
            File file;
            String data;
            String bucketName;
            String displayDame;
            do {
                data = cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_ALBUM_PROJECTION[0]));
                displayDame = cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_ALBUM_PROJECTION[1]));
                bucketName = getBucketName(data, displayDame);
                file = new File(data);
                if (!file.exists()) continue;
                if (!albumSet.contains(bucketName)) {
                    audioAlbumInfo = new AudioAlbumInfo();
                    audioAlbumInfo.setBucketName(bucketName);
                    temp.add(audioAlbumInfo);
                    albumSet.add(bucketName);
                } else {
                    audioAlbumInfo = temp.get(albumSet.indexOf(bucketName));
                    audioAlbumInfo.setCount(audioAlbumInfo.getCount() + 1);
                }
                audioAlbumInfo.setType(BaseAlbumInfo.AUDIO);

            } while (cursor.moveToPrevious());
        }
        cursor.close();
        audioAlbumInfoList.clear();
        audioAlbumInfoList.addAll(temp);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callbacks != null) callbacks.onAudioAlbumResult(audioAlbumInfoList,loaderStorageType);
            }
        });
    }
}
