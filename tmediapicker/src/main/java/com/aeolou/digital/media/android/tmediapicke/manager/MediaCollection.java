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
import com.aeolou.digital.media.android.tmediapicke.loader.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.loader.PhotoAlbumLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.PhotoLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.VideoAlbumLoader;
import com.aeolou.digital.media.android.tmediapicke.loader.VideoLoader;
import com.aeolou.digital.media.android.tmediapicke.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class MediaCollection implements MediaOperations {
    private WeakReference<Context> mContext;
    private PhotoCallbacks photoCallbacks;
    private VideoCallbacks videoCallbacks;
    private AudioCallbacks audioCallbacks;
    private LoaderMediaType loaderMediaType;
    private ContentObserver observer;
    private ExecutorService executorService;
    private Handler handler;

    public MediaCollection(WeakReference<Context> context, LoaderMediaType loaderMediaType) {
        this.mContext = context;
        this.loaderMediaType = loaderMediaType;
        executorService = Executors.newFixedThreadPool(6);
        handler = new Handler(mContext.get().getMainLooper());
        observer = getMediaContentObserver();
        switch (loaderMediaType) {
            case VIDEO:
            case VIDEO_ALBUM:
                mContext.get().getContentResolver().registerContentObserver(TConstants.VIDEO_URI, false, observer);
                break;
            case AUDIO:
            case AUDIO_ALBUM:
                mContext.get().getContentResolver().registerContentObserver(TConstants.AUDIO_URI, false, observer);
                break;
            default:
                mContext.get().getContentResolver().registerContentObserver(TConstants.PHOTO_URI, false, observer);
                break;
        }

    }


    @Override
    public void load() {
        load(loaderMediaType);
    }

    @Override
    public void load(@NonNull LoaderMediaType loaderMediaType) {
        this.loaderMediaType = loaderMediaType;
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
     * 加载所有照片
     */
    private void loadPhoto(String bucketName) {
        if (photoCallbacks != null) photoCallbacks.onStarted();
        executorService.execute(new PhotoLoader(mContext.get().getApplicationContext().getContentResolver(), bucketName, photoCallbacks, handler));
    }

    /**
     * 加载相册
     */
    private void loadPhotoAlbum() {
        if (photoCallbacks != null) photoCallbacks.onStarted();
        executorService.execute(new PhotoAlbumLoader(mContext.get().getApplicationContext().getContentResolver(), photoCallbacks, handler));
    }

    /**
     * 加载所有视频
     */
    private void loadVideo(String bucketName) {
        if (videoCallbacks != null) videoCallbacks.onStarted();
        executorService.execute(new VideoLoader(mContext.get().getApplicationContext().getContentResolver(), bucketName, videoCallbacks, handler));

    }

    /**
     * 加载视频文件专辑
     */
    private void loadVideoAlbum() {
        if (videoCallbacks != null) videoCallbacks.onStarted();
        executorService.execute(new VideoAlbumLoader(mContext.get().getApplicationContext().getContentResolver(), videoCallbacks, handler));
    }

    /**
     * 加载所有音频
     */
    private void loadAudio(String bucketName) {
        if (audioCallbacks != null) audioCallbacks.onStarted();
        executorService.execute(new AudioLoader(mContext.get().getApplicationContext().getContentResolver(), bucketName, audioCallbacks, handler));
    }

    /**
     * 加载音频文件专辑
     */
    private void loadAudioAlbum() {
        if (audioCallbacks != null) audioCallbacks.onStarted();
        executorService.execute(new AudioAlbumLoader(mContext.get().getApplicationContext().getContentResolver(), audioCallbacks, handler));
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
        mContext.get().getContentResolver().unregisterContentObserver(observer);
        executorService.shutdown();
        observer = null;
    }


    private ContentObserver getMediaContentObserver() {
        return new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                LogUtils.i("有数据变化");
                load();
            }
        };
    }

}