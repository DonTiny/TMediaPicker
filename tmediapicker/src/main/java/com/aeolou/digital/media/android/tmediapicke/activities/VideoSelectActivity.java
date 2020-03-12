package com.aeolou.digital.media.android.tmediapicke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.adapter.VideoSelectAdapter;
import com.aeolou.digital.media.android.tmediapicke.base.TBaseActivity;
import com.aeolou.digital.media.android.tmediapicke.callbacks.VideoCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.loader.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaData;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaDataBuilder;
import com.aeolou.digital.media.android.tmediapicke.models.VideoAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */

public class VideoSelectActivity extends TBaseActivity implements VideoCallbacks, View.OnClickListener {
    private List<VideoInfo> videoInfoList;
    private TextView mTv_error;
    private ProgressBar mPb_progress;
    private RecyclerView recyclerView;
    private VideoSelectAdapter adapter;
    private TMediaData tMediaData;
    private ImageView mIv_return;
    private TextView mTv_title;
    private TextView mTv_selected;
    private TextView mTv_confirm;
    private VideoAlbumInfo videoAlbumInfo;
    private boolean isShowSelected;
    private int selected = 0;
    private int selectLimit;

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tMediaData.clear();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            videoAlbumInfo = extras.getParcelable(TConstants.INTENT_EXTRA_VIDEO_AlBUM);
            selectLimit = extras.getInt(TConstants.INTENT_EXTRA_LIMIT, TConstants.DEFAULT_LIMIT);
            if (selectLimit < 0) selectLimit = TConstants.DEFAULT_LIMIT;
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_video_select;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        videoInfoList = new ArrayList<>();
        adapter = new VideoSelectAdapter(this, videoInfoList, selectLimit);
        isShowSelected = getResources().getBoolean(R.bool.tMediaPickerIsShowSelected);
    }

    @Override
    protected void initView() {
        mTv_error = (TextView) findViewById(R.id.tv_error);
        mPb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        mIv_return = (ImageView) findViewById(R.id.iv_return);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_selected = (TextView) findViewById(R.id.tv_selected);
        mTv_confirm = (TextView) findViewById(R.id.tv_confirm);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_video_select);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        updateUi();
        tMediaData = new TMediaDataBuilder(this).setDefLoaderMediaType(LoaderMediaType.VIDEO).build();
    }

    @Override
    protected void initData() {
        if (videoAlbumInfo == null) {
            mTv_title.setText(R.string.text_video);
        } else {
            mTv_title.setText(videoAlbumInfo.getBucketName());
        }
    }

    @Override
    protected void initListener() {
        tMediaData.setVideoCallbacks(this);
        adapter.setOnItemClickListener(new VideoSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, VideoInfo videoInfo, int selectedNum, int position) {
                selected = selectedNum;
                updateUi();
            }
        });
        mIv_return.setOnClickListener(this);
        mTv_confirm.setOnClickListener(this);
        mTv_selected.setOnClickListener(this);
        checkPermission();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }

            default: {
                return false;
            }
        }
    }


    @Override
    public void onStarted() {
        mPb_progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onError(Throwable throwable) {
        mPb_progress.setVisibility(View.INVISIBLE);
        mTv_error.setVisibility(View.VISIBLE);
    }


    @Override
    protected void permissionGranted() {
        if (videoAlbumInfo == null) {
            tMediaData.load();
        } else {
            tMediaData.loadAlbum(videoAlbumInfo.getBucketName());
        }
    }

    @Override
    protected void hideViews() {
        mPb_progress.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void updateUi() {
        if (selected > 0) {
            mTv_confirm.setVisibility(View.VISIBLE);
        } else {
            mTv_confirm.setVisibility(View.INVISIBLE);
        }
        if (isShowSelected && selected > 0) {
            mTv_selected.setVisibility(View.VISIBLE);
            if (selectLimit == 0) {
                mTv_selected.setText(getString(R.string.text_selected, selected + ""));
            } else {
                mTv_selected.setText(getString(R.string.text_selected_limit, selected + "", selectLimit + ""));
            }
        } else {
            mTv_selected.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_return) {
            finish();
        } else if (id == R.id.tv_confirm || id == R.id.tv_selected) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(TConstants.INTENT_EXTRA_VIDEO, adapter.getSelectedVideoInfoList());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onVideoResult(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
        mPb_progress.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setListData(this.videoInfoList);
    }

    @Override
    public void onVideoAlbumResult(List<VideoAlbumInfo> videoAlbumInfoList) {

    }
}
