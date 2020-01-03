package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.models.PhotoInfo;
import com.aeolou.digital.media.android.tmediapicke.utils.LogUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoSelectAdapter extends SimpleRecycleViewAdapter<PhotoInfo, PhotoSelectAdapter.PhotoSelectViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<PhotoInfo> selectedPhotoInfoList;
    private LinkedList<Integer> refreshPosition;
    private int selectLimit;

    public PhotoSelectAdapter(Context context, List<PhotoInfo> photoInfoList, int selectLimit) {
        super(context, photoInfoList);
        this.selectLimit = selectLimit;
        selectedPhotoInfoList = new ArrayList<>();
        refreshPosition = new LinkedList<>();
    }

    @Override
    protected PhotoSelectViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new PhotoSelectViewHolder(inflater.inflate(R.layout.recycle_photo_select_item, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(PhotoSelectViewHolder photoSelectViewHolderp, int position) {
        photoSelectViewHolderp.initView(context, listData.get(position), position);
    }

    class PhotoSelectViewHolder extends RecyclerView.ViewHolder {
        ImageView mIv_image;
        TextView mTv_select;

        public PhotoSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            mTv_select = itemView.findViewById(R.id.tv_select);
        }

        public void initView(Context context, final PhotoInfo photoInfo, final int position) {

            if (selectedPhotoInfoList.indexOf(photoInfo) != -1) {
                mTv_select.setText(selectedPhotoInfoList.indexOf(photoInfo) + 1 + "");
                mTv_select.setVisibility(View.VISIBLE);
            } else {
                mTv_select.setVisibility(View.INVISIBLE);
            }

            Glide.with(context).load(photoInfo.getData())
                    .placeholder(R.drawable.image_placeholder)
                    .into(mIv_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (selectedPhotoInfoList.size() == selectLimit && selectedPhotoInfoList.indexOf(photoInfo) == -1 && selectLimit != 0) {
                        return;
                    }
                    if (selectedPhotoInfoList.indexOf(photoInfo) != -1) {
                        selectedPhotoInfoList.remove(photoInfo);
                        refreshPosition.remove((Integer) position);
                        notifyItemChanged(position);
                    } else {
                        selectedPhotoInfoList.add(photoInfo);
                        refreshPosition.add(position);
                    }
                    refreshSelect();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, photoInfo, selectedPhotoInfoList.size(), position);
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

    public void setSelectedPhotoInfoList(ArrayList<PhotoInfo> selectedPhotoInfoList) {
        this.selectedPhotoInfoList = selectedPhotoInfoList;
    }

    public ArrayList<PhotoInfo> getSelectedPhotoInfoList() {
        return selectedPhotoInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, PhotoInfo photoInfo, int selectedNum, int position);
    }

}
