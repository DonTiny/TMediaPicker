package com.aeolou.digital.media.android.tmediapicke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public abstract class SimpleRecycleViewAdapter<T, ItemHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final LayoutInflater inflater;
    protected Context context;
    protected List<T> listData;


    public SimpleRecycleViewAdapter(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItemViewHolder((ItemHolder) holder, holder.getLayoutPosition());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void refreshListData() {
        notifyDataSetChanged();
    }

    /**
     * 加载数据
     *
     * @param data
     */
    public void appendData(List<T> data) {
        int positionStart = listData.size();
        listData.addAll(data);
        int itemCount = listData.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(positionStart + 1, itemCount);
        }
    }

    public void setListData(List<T> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    /**
     * 创建 view
     *
     * @param parent
     * @return
     */

    protected abstract ItemHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * 给view中的控件设置数据
     *
     * @param itemHolder
     * @param position
     */
    protected abstract void onBindItemViewHolder(ItemHolder itemHolder, int position);
}
