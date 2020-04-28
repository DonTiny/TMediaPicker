package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;
import com.aeolou.digital.media.android.tmediapicke.models.BaseMediaInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioLoader extends BaseMediaLoader implements Runnable {
    private ContentResolver contentResolver;
    private List<AudioInfo> audioInfoList;
    private AudioCallbacks callbacks;
    private String bucketName;
    private Handler handler;
    private LoaderStorageType loaderStorageType;
    private Context context;

    public AudioLoader(Context context, LoaderStorageType loaderStorageType, String bucketName, AudioCallbacks callbacks, Handler handler) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        this.loaderStorageType = loaderStorageType;
        this.callbacks = callbacks;
        this.bucketName = bucketName;
        this.handler = handler;
        audioInfoList = new ArrayList<>();
    }


    private String tmp;

    private String getBucketName(String data, String displayDame) {
        tmp = data.replace((File.separator + displayDame), "");
        return tmp.substring(tmp.lastIndexOf(File.separator) + 1);
    }

    @Override
    public void run() {
        String selection;
        String[] selectionArgs;
        if (TextUtils.isEmpty(bucketName)) {
            selection = getSelectionType(context, loaderStorageType);
            selectionArgs = null;
        } else {
            selection = TConstants.AUDIO_PROJECTION[5] + " LIKE "+" '%" + bucketName + "%'"+" AND " + getSelectionType(context, loaderStorageType);
            selectionArgs = null;
        }
        Cursor cursor = contentResolver.query(TConstants.AUDIO_URI, null, selection, selectionArgs, TConstants.AUDIO_SORT_ORDER);
        if (cursor == null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callbacks != null) callbacks.onError(new Throwable("cursor is null !"));
                }
            });
            return;
        }
        audioInfoList.clear();
        if (cursor.moveToLast()) {
            File file;
            AudioInfo audioInfo;
            String bucketNameTmp;
            do {
                file = new File(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[5])));
                bucketNameTmp = getBucketName(cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[5])), cursor.getString(cursor.getColumnIndex(TConstants.AUDIO_PROJECTION[2])));
                if (!TextUtils.isEmpty(bucketName) && !bucketNameTmp.equals(bucketName)) continue;
                if (file.exists()) {
                    audioInfo = new AudioInfo();
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
                    audioInfo.setType(BaseMediaInfo.AUDIO);
                    audioInfoList.add(audioInfo);

                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callbacks != null) callbacks.onAudioResult(audioInfoList, loaderStorageType);
            }
        });
    }
}
