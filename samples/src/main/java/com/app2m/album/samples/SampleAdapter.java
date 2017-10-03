package com.app2m.album.samples;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    private View footerView;
    private Context mContext;

    public SampleAdapter(List<ItemVM> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) mContext = parent.getContext();
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
//            return new FooterViewHolder(footerView);
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

    public void setDefaultFooterView() {
        footerView = LayoutInflater.from(mContext).inflate(R.layout.sample_footer, null, false);
    }

    @Override
    public int getItemCount() {
        return mData.isEmpty() ? 0 : mData.size() + 1;
    }

    public int getRealItemCount() {
        return mData.size();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }
    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }
    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        } else {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(false);
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
