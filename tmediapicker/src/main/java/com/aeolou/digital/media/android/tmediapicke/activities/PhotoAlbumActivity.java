package com.aeolou.digital.media.android.tmediapicke.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.adapter.PhotoAlbumSelectAdapter;
import com.aeolou.digital.media.android.tmediapicke.base.TBaseActivity;
import com.aeolou.digital.media.android.tmediapicke.callbacks.PhotoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaData;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaDataBuilder;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoAlbumActivity extends TBaseActivity implements PhotoCallbacks, View.OnClickListener {
    private List<PhotoAlbumInfo> photoAlbumInfoList;
    private ImageView mIv_return;
    private ProgressBar mPb_progress;
    private TextView mTv_error;
    private RecyclerView mRecycle_album_select;
    private TextView mTv_title;
    private PhotoAlbumSelectAdapter adapter;

    private TMediaData tMediaData;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tMediaData.clear();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_photo_album;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tMediaData = new TMediaDataBuilder().setLoaderMediaType(LoaderMediaType.PHOTO_ALBUM).build();
        photoAlbumInfoList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mTv_error = (TextView) findViewById(R.id.tv_error);
        mTv_error.setVisibility(View.INVISIBLE);
        mRecycle_album_select = (RecyclerView) findViewById(R.id.recycle_album_select);
        mIv_return = (ImageView) findViewById(R.id.iv_return);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mPb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycle_album_select.setLayoutManager(layoutManager);
        adapter = new PhotoAlbumSelectAdapter(this, photoAlbumInfoList);
        mRecycle_album_select.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mTv_title.setText(R.string.text_photo_album);
    }

    @Override
    protected void initListener() {
        tMediaData.setPhotoCallbacks(this);
        mIv_return.setOnClickListener(this);
        adapter.setOnItemClickListener(new PhotoAlbumSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PhotoAlbumInfo photoAlbumInfo, int position) {
                Intent intent = new Intent(PhotoAlbumActivity.this, PhotoSelectActivity.class);
                intent.putExtra(TConstants.INTENT_EXTRA_PHOTO_AlBUM, photoAlbumInfo);
                if (getIntent().getExtras() != null) {
                    intent.putExtra(TConstants.INTENT_EXTRA_LIMIT, getIntent().getExtras().getInt(TConstants.INTENT_EXTRA_LIMIT, TConstants.DEFAULT_LIMIT));
                }
                startActivityForResult(intent, TConstants.REQUEST_PHOTO_CODE);
            }
        });
    }

    @Override
    protected void permissionGranted() {
        super.permissionGranted();
        tMediaData.load();
    }

    @Override
    public void onPhotoResult(List<PhotoInfo> photoInfoList, LoaderStorageType loaderStorageType) {

    }

    @Override
    public void onPhotoAlbumResult(List<PhotoAlbumInfo> photoAlbumInfoList, LoaderStorageType loaderStorageType) {
        this.photoAlbumInfoList = photoAlbumInfoList;
        mPb_progress.setVisibility(View.INVISIBLE);
        mRecycle_album_select.setVisibility(View.VISIBLE);
        adapter.setListData(this.photoAlbumInfoList);
    }

    @Override
    public void onStarted() {
        mPb_progress.setVisibility(View.VISIBLE);
        mRecycle_album_select.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(Throwable throwable) {
        mPb_progress.setVisibility(View.INVISIBLE);
        mTv_error.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TConstants.REQUEST_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_return) {
            finish();
        }
    }
}
