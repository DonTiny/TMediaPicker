package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.models.VideoInfo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class VideoSelectAdapter extends SimpleRecycleViewAdapter<VideoInfo, VideoSelectAdapter.VideoSelectViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<VideoInfo> selectedVideoInfoList;
    private LinkedList<Integer> refreshPosition;
    private int selectLimit;

    public VideoSelectAdapter(Context context, List<VideoInfo> videoInfoList, int selectLimit) {
        super(context, videoInfoList);
        this.selectLimit = selectLimit;
        selectedVideoInfoList = new ArrayList<>();
        refreshPosition = new LinkedList<>();
    }

    @Override
    protected VideoSelectViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new VideoSelectViewHolder(inflater.inflate(R.layout.recycle_video_select_item, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(VideoSelectViewHolder videoSelectViewHolder, int position) {
        videoSelectViewHolder.initView(context, listData.get(position), position);
    }

    class VideoSelectViewHolder extends RecyclerView.ViewHolder {
        ImageView mIv_video;
        TextView mTv_select;
        TextView mTv_time;

        public VideoSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv_video = (ImageView) itemView.findViewById(R.id.iv_video);
            mTv_select = (TextView) itemView.findViewById(R.id.tv_select);
            mTv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void initView(Context context, final VideoInfo videoInfo, final int position) {

            if (selectedVideoInfoList.indexOf(videoInfo) != -1) {
                mTv_select.setText(selectedVideoInfoList.indexOf(videoInfo) + 1 + "");
                mTv_select.setVisibility(View.VISIBLE);
            } else {
                mTv_select.setVisibility(View.INVISIBLE);
            }
            mTv_time.setText(getGapTime(videoInfo.getDuration()));
            Glide.with(context).load(videoInfo.getData())
                    .placeholder(R.drawable.image_placeholder)
                    .into(mIv_video);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedVideoInfoList.size() == selectLimit && selectedVideoInfoList.indexOf(videoInfo) == -1 && selectLimit != 0) {
                        return;
                    }
                    if (selectedVideoInfoList.indexOf(videoInfo) != -1) {
                        selectedVideoInfoList.remove(videoInfo);
                        refreshPosition.remove((Integer) position);
                        notifyItemChanged(position);
                    } else {
                        selectedVideoInfoList.add(videoInfo);
                        refreshPosition.add(position);
                    }
                    refreshSelect();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, videoInfo, selectedVideoInfoList.size(), position);
                    }
                }
            });
        }
    }

    private void refreshSelect() {
        for (Integer integer : refreshPosition) {
            notifyItemChanged(integer);
        }

    }

    public ArrayList<VideoInfo> getSelectedVideoInfoList() {
        return selectedVideoInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, VideoInfo videoInfo, int selectedNum, int position);
    }

    public String getGapTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        long second = (time - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        if (second < 10) {
            diffTime = diffTime + ":0" + second;
        } else {
            diffTime = diffTime + ":" + second;

        }
        return diffTime;
    }

}
