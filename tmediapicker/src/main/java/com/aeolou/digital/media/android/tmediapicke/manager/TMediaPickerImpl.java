package com.aeolou.digital.media.android.tmediapicke.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.activities.AudioAlbumActivity;
import com.aeolou.digital.media.android.tmediapicke.activities.AudioSelectActivity;
import com.aeolou.digital.media.android.tmediapicke.activities.PhotoAlbumActivity;
import com.aeolou.digital.media.android.tmediapicke.activities.PhotoSelectActivity;
import com.aeolou.digital.media.android.tmediapicke.activities.VideoAlbumActivity;
import com.aeolou.digital.media.android.tmediapicke.activities.VideoSelectActivity;
import com.aeolou.digital.media.android.tmediapicke.callbacks.MediaSelectResultCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;

import static android.app.Activity.RESULT_OK;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class TMediaPickerImpl implements TMediaPicker {

    private Activity currentActivity;

    public TMediaPickerImpl(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {

        }
    }

    @Override
    public void toMediaPage(@NonNull PageType pageType, int selectLimit) {
        Intent intent = null;
        int requestCode = 0;
        switch (pageType) {
            case PHOTO_ALL:
                intent = new Intent(currentActivity, PhotoSelectActivity.class);
                requestCode = TConstants.REQUEST_PHOTO_CODE;
                break;
            case PHOTO_ALBUM:
                intent = new Intent(currentActivity, PhotoAlbumActivity.class);
                requestCode = TConstants.REQUEST_PHOTO_CODE;
                break;
            case VIDEO_ALL:
                intent = new Intent(currentActivity, VideoSelectActivity.class);
                requestCode = TConstants.REQUEST_VIDEO_CODE;
                break;
            case VIDEO_ALBUM:
                intent = new Intent(currentActivity, VideoAlbumActivity.class);
                requestCode = TConstants.REQUEST_VIDEO_CODE;
                break;
            case AUDIO_ALL:
                intent = new Intent(currentActivity, AudioSelectActivity.class);
                requestCode = TConstants.REQUEST_AUDIO_CODE;
                break;
            case AUDIO_ALBUM:
                intent = new Intent(currentActivity, AudioAlbumActivity.class);
                requestCode = TConstants.REQUEST_AUDIO_CODE;
                break;
        }
        if (intent != null) {
            intent.putExtra(TConstants.INTENT_EXTRA_LIMIT, selectLimit);
            currentActivity.startActivityForResult(intent, requestCode);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, @NonNull MediaSelectResultCallbacks callbacks) {
        if (requestCode == TConstants.REQUEST_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            callbacks.onPhotoResult(data.<PhotoInfo>getParcelableArrayListExtra(TConstants.INTENT_EXTRA_PHOTO));
        } else if (requestCode == TConstants.REQUEST_VIDEO_CODE && resultCode == RESULT_OK && data != null) {
            callbacks.onVideoResult(data.<VideoInfo>getParcelableArrayListExtra(TConstants.INTENT_EXTRA_VIDEO));
        } else if (requestCode == TConstants.REQUEST_AUDIO_CODE && resultCode == RESULT_OK && data != null) {
            callbacks.onAudioResult(data.<AudioInfo>getParcelableArrayListExtra(TConstants.INTENT_EXTRA_AUDIO));
        }
    }


}
