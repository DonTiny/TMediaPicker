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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoSelectAdapter extends SimpleRecycleViewAdapter<PhotoInfo, PhotoSelectAdapter.PhotoSelectViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<String> selectPhotoIds;
    private ArrayList<Integer> refreshPosition;
    private int selectLimit;

    public PhotoSelectAdapter(Context context, List<PhotoInfo> photoInfoList, int selectLimit) {
        super(context, photoInfoList);
        this.selectLimit = selectLimit;
        selectPhotoIds = new ArrayList<>();
        refreshPosition = new ArrayList<>();
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

            if (selectPhotoIds.indexOf(photoInfo.getId()) != -1) {
                mTv_select.setText(selectPhotoIds.indexOf(photoInfo.getId()) + 1 + "");
                mTv_select.setVisibility(View.VISIBLE);
            } else {
                mTv_select.setVisibility(View.INVISIBLE);
            }

            Glide.with(context).load(photoInfo.getThumbnailsData())
                    .placeholder(R.drawable.image_placeholder)
                    .into(mIv_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (selectPhotoIds.size() == selectLimit && selectPhotoIds.indexOf(photoInfo.getId()) == -1 && selectLimit != 0) {
                        return;
                    }
                    if (selectPhotoIds.indexOf(photoInfo.getId()) != -1) {
                        selectPhotoIds.remove(photoInfo.getId());
                        refreshPosition.remove((Integer) position);
                        notifyItemChanged(position);
                    } else {
                        selectPhotoIds.add(photoInfo.getId());
                        refreshPosition.add(position);
                    }
                    refreshSelect();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, photoInfo, selectPhotoIds.size(), position);
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


    public ArrayList<PhotoInfo> getSelectedPhotoInfoList() {
        ArrayList<PhotoInfo> selectedPhotoInfoList = new ArrayList<>();
        for (PhotoInfo bean : listData) {
            if (selectPhotoIds.indexOf(bean.getId()) != -1) {
                selectedPhotoInfoList.add(bean);
            }
        }
        return selectedPhotoInfoList;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, PhotoInfo photoInfo, int selectedNum, int position);
    }

}
