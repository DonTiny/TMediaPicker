package com.aeolou.digital.media.android.tmediapicke.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;


import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.adapter.AudioSelectAdapter;
import com.aeolou.digital.media.android.tmediapicke.adapter.RecycleViewDivider;
import com.aeolou.digital.media.android.tmediapicke.base.TBaseActivity;
import com.aeolou.digital.media.android.tmediapicke.callbacks.AudioCallbacks;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.loader.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaData;
import com.aeolou.digital.media.android.tmediapicke.manager.TMediaDataBuilder;
import com.aeolou.digital.media.android.tmediapicke.models.AudioAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioSelectActivity extends TBaseActivity implements View.OnClickListener, AudioCallbacks {
    private List<AudioInfo> audioInfoList;
    private TextView mTv_error;
    private ProgressBar mPb_progress;
    private RecyclerView recyclerView;
    private AudioSelectAdapter adapter;
    private TMediaData tMediaData;
    private ImageView mIv_return;
    private TextView mTv_title;
    private TextView mTv_selected;
    private TextView mTv_confirm;
    private AudioAlbumInfo audioAlbumInfo;
    private boolean isShowSelected;
    private int selected = 0;
    private int selectLimit;

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
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            audioAlbumInfo = extras.getParcelable(TConstants.INTENT_EXTRA_AUDIO_AlBUM);
            selectLimit = extras.getInt(TConstants.INTENT_EXTRA_LIMIT, TConstants.DEFAULT_LIMIT);
            if (selectLimit < 0) selectLimit = TConstants.DEFAULT_LIMIT;
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_audio_select;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tMediaData = new TMediaDataBuilder(this).setDefLoaderMediaType(LoaderMediaType.AUDIO).build();
        audioInfoList = new ArrayList<>();
        adapter = new AudioSelectAdapter(this, audioInfoList,selectLimit);
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
        recyclerView = (RecyclerView) findViewById(R.id.recycle_audio_select);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 1, mContext.getResources().getColor(R.color.tMediaPickerDividerLine), false));
        recyclerView.setAdapter(adapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        updateUi();
    }


    @Override
    protected void initData() {
        if (audioAlbumInfo == null) {
            mTv_title.setText(R.string.text_music);
        } else {
            mTv_title.setText(audioAlbumInfo.getBucketName());
        }
    }

    @Override
    protected void initListener() {
        tMediaData.setAudioCallbacks(this);
        adapter.setOnItemSelectedClickListener(new AudioSelectAdapter.OnItemSelectedClickListener() {
            @Override
            public void onItemClick(View view, int selectedNumber, AudioInfo audioInfo, int position) {
                selected = selectedNumber;
                updateUi();
            }

        });
        mIv_return.setOnClickListener(this);
        mTv_confirm.setOnClickListener(this);
        mTv_selected.setOnClickListener(this);
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
        if (audioAlbumInfo == null) {
            tMediaData.load();
        } else {
            tMediaData.loadAlbum(audioAlbumInfo.getBucketName());
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
            intent.putParcelableArrayListExtra(TConstants.INTENT_EXTRA_AUDIO, adapter.getSelectedItem());
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Override
    public void onAudioResult(List<AudioInfo> audioInfoList) {
        this.audioInfoList = audioInfoList;
        mPb_progress.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setListData(this.audioInfoList);
    }

    @Override
    public void onAudioAlbumResult(List<AudioAlbumInfo> audioAlbumInfoList) {

    }
}
