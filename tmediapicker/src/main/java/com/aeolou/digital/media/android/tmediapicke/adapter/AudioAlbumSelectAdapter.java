package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.models.AudioAlbumInfo;
import com.aeolou.digital.media.android.tmediapicke.models.VideoAlbumInfo;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioAlbumSelectAdapter extends SimpleRecycleViewAdapter<AudioAlbumInfo, AudioAlbumSelectAdapter.AudioAlbumViewHolder> {

    private OnItemClickListener onItemClickListener;

    public AudioAlbumSelectAdapter(Context context, List<AudioAlbumInfo> listData) {
        super(context, listData);
    }

    @Override
    protected AudioAlbumViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new AudioAlbumViewHolder(inflater.inflate(R.layout.recycle_audio_album_select_item, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(AudioAlbumViewHolder audioAlbumViewHolder, int position) {
        audioAlbumViewHolder.initView(context, listData.get(position), position);
    }


    class AudioAlbumViewHolder extends RecyclerView.ViewHolder {
        TextView mTv_album_name;
        TextView mTv_album_count;
        ImageView mIv_album_arrow;

        public AudioAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv_album_name = (TextView) itemView.findViewById(R.id.tv_album_name);
            mTv_album_count = (TextView) itemView.findViewById(R.id.tv_album_count);
            mIv_album_arrow = (ImageView) itemView.findViewById(R.id.iv_album_arrow);
        }

        public void initView(Context context, final AudioAlbumInfo audioAlbumInfo, final int position) {
            mTv_album_name.setText(audioAlbumInfo.getBucketName());
            mTv_album_count.setText(audioAlbumInfo.getCount() + "");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, audioAlbumInfo, position);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, AudioAlbumInfo audioAlbumInfo, int position);
    }

}
