package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.models.VideoAlbumInfo;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */

public class VideoAlbumSelectAdapter extends SimpleRecycleViewAdapter<VideoAlbumInfo, VideoAlbumSelectAdapter.VideoAlbumViewHolder> {

    private OnItemClickListener onItemClickListener;

    public VideoAlbumSelectAdapter(Context context, List<VideoAlbumInfo> listData) {
        super(context, listData);
    }

    @Override
    protected VideoAlbumViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new VideoAlbumViewHolder(inflater.inflate(R.layout.recycle_album_select_item, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(VideoAlbumViewHolder videoAlbumViewHolder, int position) {
        videoAlbumViewHolder.initView(context, listData.get(position), position);
    }


    class VideoAlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView mIv_album_image;
        TextView mTv_album_name;
        TextView mTv_album_count;
        ImageView mIv_album_arrow;

        public VideoAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv_album_image = (ImageView) itemView.findViewById(R.id.iv_album_image);
            mTv_album_name = (TextView) itemView.findViewById(R.id.tv_album_name);
            mTv_album_count = (TextView) itemView.findViewById(R.id.tv_album_count);
            mIv_album_arrow = (ImageView) itemView.findViewById(R.id.iv_album_arrow);
        }

        public void initView(Context context, final VideoAlbumInfo videoAlbumInfo, final int position) {
            mTv_album_name.setText(videoAlbumInfo.getBucketName());
            mTv_album_count.setText(videoAlbumInfo.getCount() + "");
            Glide.with(context)
                    .load(videoAlbumInfo.getPreviewUri())
                    .placeholder(R.drawable.image_placeholder).centerCrop().into(mIv_album_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, videoAlbumInfo, position);
                    }
                }
            });
        }
    }

    public void releaseResources() {
        listData = null;
        context = null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, VideoAlbumInfo videoAlbumInfo, int position);
    }

}
