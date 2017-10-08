package com.app2m.album.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app2m.album.BR;
import com.app2m.album.R;
import com.app2m.album.bean.MediaBean;
import com.app2m.album.vm.MediaVM;

import java.util.List;

/**
 * Created by CongHao on 2017/10/8.
 * E-mail: hao.cong@app2m.com
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ItemViewHolder> {

    private final List<MediaVM> mData;

    public MediaAdapter(List<MediaVM> data) {
        mData = data;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.album_image_item, parent, false);
        //添加监听器
        binding.setVariable(BR.adapter, this);
        ItemViewHolder holder = new ItemViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
}
