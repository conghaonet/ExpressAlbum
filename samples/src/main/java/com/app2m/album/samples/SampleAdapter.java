package com.app2m.album.samples;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CongHao on 2017/10/2.
 * E-mail: hao.cong@app2m.com
 */

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    private final List<ItemVM> mData;
    private boolean hasFooter;

    public SampleAdapter(List<ItemVM> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.sample_item, parent, false);
            //添加监听器
            binding.setVariable(BR.adapter, this);
            ItemViewHolder holder = new ItemViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        } else if(viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_footer, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position<mData.size() && holder instanceof ItemViewHolder) {
            ((ItemViewHolder)holder).binding.setVariable(BR.item, mData.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mData.isEmpty() ? 0 : mData.size() + 1;
/*
        if(hasFooter) {
        } else {
            return mData.size();
        }
*/
    }

    public int getRealItemCount() {
        return mData.size();
    }


    public boolean isHasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
        if(this.hasFooter) {
//            this.notifyDataSetChanged();
        } else {
//            if(getRealItemCount() + 1 == getItemCount()) {
//                this.notifyItemRemoved(getItemCount());
//            }
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }
}
