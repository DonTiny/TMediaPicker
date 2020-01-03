package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<AudioInfo> audioInfos;
    private AudioCallbacks callbacks;
    private String bucketName;
    private Handler handler;

    public AudioLoader(ContentResolver contentResolver, String bucketName, AudioCallbacks callbacks, Handler handler) {
        this.contentResolver = contentResolver;
        this.callbacks = callbacks;
        this.bucketName = bucketName;
        this.handler = handler;
        audioInfos = new ArrayList<>();
    }


    private String tmp;

    private String getBucketName(String data, String displayDame) {
        tmp = data.replace((File.separator + displayDame), "");
        return tmp.substring(tmp.lastIndexOf(File.separator) + 1);
    }

    @Override
    public void run() {
        Cursor cursor = contentResolver.query(TConstants.AUDIO_URI, null, null, null, TConstants.AUDIO_SORT_ORDER);
        if (cursor == null) {
            if (callbacks != null) {
                callbacks.onError(new Throwable("cursor is null !"));
            }
            return;
        }
        audioInfos.clear();
        if (cursor.moveToLast()) {
            File file;
            AudioInfo audioInfo;
            String bucketNameTmp;
            do {
                file = new File(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[5])));
                bucketNameTmp = getBucketName(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[5])), cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[2])));
                audioInfo = new AudioInfo();
                if (!TextUtils.isEmpty(bucketName) && !bucketNameTmp.equals(bucketName)) continue;
                if (file.exists()) {
                    audioInfo.setId(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[0])));
                    audioInfo.setTitle(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[1])));
                    audioInfo.setDisplayName(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[2])));
                    audioInfo.setArtist(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[3])));
                    audioInfo.setDuration(cursor.getLong(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[4])));
                    audioInfo.setData(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[5])));
                    audioInfo.setSize(cursor.getLong(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[6])));
                    audioInfo.setAlbum(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[7])));
                    audioInfo.setAlbumId(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[8])));
                    audioInfo.setTrack(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[9])));
                    audioInfo.setBookmark(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[10])));
                    audioInfo.setDateAdded(cursor.getLong(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[11])));
                    audioInfo.setDateModified(cursor.getLong(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[12])));
                    audioInfo.setMimeType(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[13])));
                    audioInfos.add(audioInfo);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        handler.post(new Runnable() {
            @Override
            public void run() {
                callbacks.onAudioResult(audioInfos);
            }
        });
    }
}
