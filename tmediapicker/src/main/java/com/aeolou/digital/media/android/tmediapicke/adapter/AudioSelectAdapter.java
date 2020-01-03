package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.models.AudioInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioSelectAdapter extends SimpleRecycleViewAdapter<AudioInfo, AudioSelectAdapter.AudioSelectViewHolder> {

    private int selectLimit;

    private OnItemSelectedClickListener onItemSelectedClickListener;
    private ArrayList<AudioInfo> selectedVideoInfoList;
    private LinkedList<Integer> refreshPosition;


    public AudioSelectAdapter(Context context, List<AudioInfo> listData, int selectLimit) {
        super(context, listData);
        this.selectLimit = selectLimit;
        selectedVideoInfoList = new ArrayList<>();
        refreshPosition = new LinkedList<>();
    }

    @Override
    protected AudioSelectViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new AudioSelectViewHolder(inflater.inflate(R.layout.recycle_audio_select_item, parent, false));
    }

    @Override
    protected void onBindItemViewHolder(AudioSelectViewHolder audioSelectViewHolder, int position) {
        audioSelectViewHolder.initView(context, listData.get(position), position);
    }


    class AudioSelectViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv_title;
        private CheckBox mCb_select;
        private StringBuilder setText;

        public AudioSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv_title = (TextView) itemView.findViewById(R.id.tv_title);
            mCb_select = (CheckBox) itemView.findViewById(R.id.cb_select);
            setText = new StringBuilder();
        }

        public void initView(Context context, final AudioInfo audioInfo, final int position) {
            setText = new StringBuilder();
            setText.append(audioInfo.getTitle()).append(" - ").append(audioInfo.getArtist());
            mCb_select.setChecked(selectedVideoInfoList.indexOf(audioInfo) != -1);
            mTv_title.setText(setText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedVideoInfoList.size() == selectLimit && selectedVideoInfoList.indexOf(audioInfo) == -1 && selectLimit != 0) {
                        return;
                    }
                    if (selectedVideoInfoList.indexOf(audioInfo) != -1) {
                        selectedVideoInfoList.remove(audioInfo);
                        refreshPosition.remove((Integer) position);
                        notifyItemChanged(position);
                    } else {
                        selectedVideoInfoList.add(audioInfo);
                        refreshPosition.add(position);
                    }
                    if (onItemSelectedClickListener != null) {
                        onItemSelectedClickListener.onItemClick(view, selectedVideoInfoList.size(), audioInfo, position);
                    }
                    refreshSelect();
                    mCb_select.setChecked(!mCb_select.isChecked());
                }
            });
        }
    }

    private void refreshSelect() {
        for (Integer integer : refreshPosition) {
            notifyItemChanged(integer);
        }

    }

    public ArrayList<AudioInfo> getSelectedItem() {
        return selectedVideoInfoList;
    }


    public void setOnItemSelectedClickListener(OnItemSelectedClickListener onItemSelectedClickListener) {
        this.onItemSelectedClickListener = onItemSelectedClickListener;
    }

    public interface OnItemSelectedClickListener {
        void onItemClick(View view, int selectedNumber, AudioInfo audioInfo, int position);
    }


}
