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
import java.util.List;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioSelectAdapter extends SimpleRecycleViewAdapter<AudioInfo, AudioSelectAdapter.AudioSelectViewHolder> {

    private int selectedNumber = 0;

    private int selectLimit;

    private OnItemSelectedClickListener onItemSelectedClickListener;
    private ArrayMap<Integer, Boolean> mSelectedPositions;

    public AudioSelectAdapter(Context context, List<AudioInfo> listData, int selectLimit) {
        super(context, listData);
        this.selectLimit = selectLimit;
        mSelectedPositions = new ArrayMap<>();
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
            if (mSelectedPositions.get(position) == null) {
                mSelectedPositions.put(position, false);
            }
            mCb_select.setChecked(mSelectedPositions.get(position));
            mTv_title.setText(setText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mSelectedPositions.get(position)) {
                        mSelectedPositions.put(position, false);
                        selectedNumber--;
                    } else {
                        if (selectedNumber >= selectLimit) {
                            return;
                        }
                        mSelectedPositions.put(position, true);
                        selectedNumber++;
                    }
                    if (onItemSelectedClickListener != null) {
                        onItemSelectedClickListener.onItemClick(itemView, selectedNumber, audioInfo, position);
                    }
                    mCb_select.setChecked(!mCb_select.isChecked());
                }
            });
        }
    }

    public ArrayList<AudioInfo> getSelectedItem() {
        ArrayList<AudioInfo> selectList = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {
            if (mSelectedPositions.get(i) != null && mSelectedPositions.get(i)) {
                selectList.add(listData.get(i));
            }
        }
        return selectList;
    }

    public void clearSelected() {
        selectedNumber = 0;
        mSelectedPositions.clear();
        notifyDataSetChanged();
    }


    public void setOnItemSelectedClickListener(OnItemSelectedClickListener onItemSelectedClickListener) {
        this.onItemSelectedClickListener = onItemSelectedClickListener;
    }

    public interface OnItemSelectedClickListener {
        void onItemClick(View view, int selectedNumber, AudioInfo audioInfo, int position);
    }


}
