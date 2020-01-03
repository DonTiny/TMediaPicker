package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoAlbumInfo;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoAlbumSelectAdapter extends SimpleRecycleViewAdapter<PhotoAlbumInfo, PhotoAlbumSelectAdapter.PhotoAlbumViewHolder> {

    private OnItemClickListener onItemClickListener;

    public PhotoAlbumSelectAdapter(Context context, List<PhotoAlbumInfo> listData) {
        super(context, listData);
    }

    @Override
    protected PhotoAlbumViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new PhotoAlbumViewHolder(inflater.inflate(R.layout.recycle_album_select_item, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(PhotoAlbumViewHolder photoAlbumViewHolder, int position) {
        photoAlbumViewHolder.initView(context, listData.get(position), position);
    }


    class PhotoAlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView mIv_album_image;
        TextView mTv_album_name;
        TextView mTv_album_count;
        ImageView mIv_album_arrow;

        public PhotoAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv_album_image = (ImageView) itemView.findViewById(R.id.iv_album_image);
            mTv_album_name = (TextView) itemView.findViewById(R.id.tv_album_name);
            mTv_album_count = (TextView) itemView.findViewById(R.id.tv_album_count);
            mIv_album_arrow = (ImageView) itemView.findViewById(R.id.iv_album_arrow);
        }

        public void initView(Context context, final PhotoAlbumInfo photoAlbumInfo, final int position) {
            mTv_album_name.setText(photoAlbumInfo.getBucketName());
            mTv_album_count.setText(photoAlbumInfo.getCount() + "");
            Glide.with(context)
                    .load(photoAlbumInfo.getPreviewPath())
                    .placeholder(R.drawable.image_placeholder).centerCrop().into(mIv_album_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, photoAlbumInfo, position);
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
        void onItemClick(View view, PhotoAlbumInfo photoAlbumInfo, int position);
    }

}
