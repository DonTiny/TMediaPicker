package com.aeolou.digital.media.android.tmediapicke.manager;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.loader.AudioAlbumLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.AudioLoader;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.loader.PhotoAlbumLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.PhotoLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.VideoAlbumLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.VideoLoader;
import com.aeolou.digital.media.android.tmediapicke.provider.ContextManager;
import com.aeolou.digital.media.android.tmediapicke.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class MediaCollection implements MediaOperations, ContextManager.OnScanSDListener {
    private Context mContext;
    private PhotoCallbacks photoCallbacks;
    private VideoCallbacks videoCallbacks;
    private AudioCallbacks audioCallbacks;
    private LoaderMediaType loaderMediaType;
    private LoaderStorageType loaderStorageType;
    private ContentObserver observer;
    private ExecutorService executorService;
    private Handler handler;

    public MediaCollection(Context context, LoaderMediaType loaderMediaType, LoaderStorageType loaderStorageType) {
        LogUtils.i("创建加载");
        this.mContext = context;
        this.loaderMediaType = loaderMediaType;
        this.loaderStorageType = loaderStorageType;
        executorService = Executors.newFixedThreadPool(10);
        handler = new Handler(mContext.getMainLooper());
        observer = getMediaContentObserver();
        switch (loaderMediaType) {
            case VIDEO:
            case VIDEO_ALBUM:
                mContext.getContentResolver().registerContentObserver(TConstants.VIDEO_URI, false, observer);
                break;
            case AUDIO:
            case AUDIO_ALBUM:
                mContext.getContentResolver().registerContentObserver(TConstants.AUDIO_URI, false, observer);
                break;
            default:
                mContext.getContentResolver().registerContentObserver(TConstants.PHOTO_URI, false, observer);
                break;
        }
        ContextManager.get().addOnScanSDListenerList(this);
    }


    @Override
    public void load() {
        load(loaderMediaType);
    }

    @Override
    public void load(@NonNull LoaderMediaType loaderMediaType) {
        this.loaderMediaType = loaderMediaType;
        LogUtils.i("加载类型" + loaderMediaType + "的数据");
        if (mContext == null || executorService == null) return;
        switch (loaderMediaType) {
            case PHOTO:
                loadPhoto(null);
                break;
            case PHOTO_ALBUM:
                loadPhotoAlbum();
                break;
            case VIDEO:
                loadVideo(null);
                break;
            case VIDEO_ALBUM:
                loadVideoAlbum();
                break;
            case AUDIO:
                loadAudio(null);
                break;
            case AUDIO_ALBUM:
                loadAudioAlbum();
                break;
        }
    }

    /**
     * 加载指定专辑下的数据
     *
     * @param bucketName 只有传入当前加载类型（LoaderMediaType）的专辑名称才能正确加载数据
     */
    @Override
    public void loadAlbum(@NonNull String bucketName) {
        loadAlbum(bucketName, loaderMediaType);
    }

    @Override
    public void loadAlbum(@NonNull String bucketName, @NonNull LoaderMediaType loaderMediaType) {
        LogUtils.i("加载专辑" + bucketName + "的数据");
        this.loaderMediaType = loaderMediaType;
        switch (loaderMediaType) {
            case PHOTO:
                loadPhoto(bucketName);
                break;
            case VIDEO:
                loadVideo(bucketName);
                break;
            case AUDIO:
                loadAudio(bucketName);
                break;
        }
    }


    /**
     * 加载照片
     */
    private void loadPhoto(String bucketName) {
        if (photoCallbacks != null) photoCallbacks.onStarted();
        executorService.execute(new PhotoLoader(mContext.getApplicationContext(), loaderStorageType, bucketName, photoCallbacks, handler));
    }

    /**
     * 加载相册
     */
    private void loadPhotoAlbum() {
        if (photoCallbacks != null) photoCallbacks.onStarted();
        executorService.execute(new PhotoAlbumLoader(mContext.getApplicationContext(), loaderStorageType, photoCallbacks, handler));
    }

    /**
     * 加载视频
     */
    private void loadVideo(String bucketName) {
        if (videoCallbacks != null) videoCallbacks.onStarted();
        executorService.execute(new VideoLoader(mContext.getApplicationContext(), loaderStorageType, bucketName, videoCallbacks, handler));

    }

    /**
     * 加载视频文件专辑
     */
    private void loadVideoAlbum() {
        if (videoCallbacks != null) videoCallbacks.onStarted();
        executorService.execute(new VideoAlbumLoader(mContext, loaderStorageType, videoCallbacks, handler));
    }

    /**
     * 加载音频
     */
    private void loadAudio(String bucketName) {
        if (audioCallbacks != null) audioCallbacks.onStarted();
        executorService.execute(new AudioLoader(mContext.getApplicationContext(), loaderStorageType, bucketName, audioCallbacks, handler));
    }

    /**
     * 加载音频文件专辑
     */
    private void loadAudioAlbum() {
        if (audioCallbacks != null) audioCallbacks.onStarted();
        executorService.execute(new AudioAlbumLoader(mContext, loaderStorageType, audioCallbacks, handler));
    }


    @Override
    public void setPhotoCallbacks(PhotoCallbacks photoCallbacks) {
        this.photoCallbacks = photoCallbacks;
    }

    @Override
    public void setVideoCallbacks(VideoCallbacks videoCallbacks) {
        this.videoCallbacks = videoCallbacks;
    }

    @Override
    public void setAudioCallbacks(AudioCallbacks audioCallbacks) {
        this.audioCallbacks = audioCallbacks;
    }

    public void clear() {
        LogUtils.i("清楚加载");
        mContext.getContentResolver().unregisterContentObserver(observer);
        executorService.shutdown();
        executorService = null;
        observer = null;
    }

    public void setLoaderStorageType(LoaderStorageType loaderStorageType) {
        this.loaderStorageType = loaderStorageType;
    }

    private ContentObserver getMediaContentObserver() {
        return new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                LogUtils.i(uri + "有数据" + selfChange + "变化" + uri.getPath());

            }
        };
    }

    @Override
    public void onScanStarted() {

    }

    @Override
    public void onScanFinished() {
        load();
    }
}