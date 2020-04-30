package com.aeolou.android.tmediapicke;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaData;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaDataBuilder;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;

import java.util.List;

public class MediaDataActivity extends AppCompatActivity implements PhotoCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_data);
        TMediaData tMediaData = new TMediaDataBuilder()
                .setLoaderMediaType(LoaderMediaType.PHOTO)
                .setLoaderStorageType(LoaderStorageType.ALL)
                .build();
        tMediaData.setPhotoCallbacks(this);
        tMediaData.load();
    }


    @Override
    public void onPhotoResult(List<PhotoInfo> photoInfoList, LoaderStorageType loaderStorageType) {

    }

    @Override
    public void onPhotoAlbumResult(List<PhotoAlbumInfo> photoAlbumInfoList, LoaderStorageType loaderStorageType) {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onError(Throwable throwable) {


    }
}
