package com.app2m.album.samples;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.app2m.album.samples.databinding.SampleActivityPullUpRefreshBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PullUpRefreshActivity extends AppCompatActivity {
    private static final String TAG = PullUpRefreshActivity.class.getName();
    private SampleActivityPullUpRefreshBinding mBinding;
    private static final int ROWS_LIMIT = 6;
    private static final int GRID_SPAN_COUNT = 3;
    private SampleAdapter adapter;
    private final List<ItemVM> mData = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.sample_activity_pull_up_refresh);
        mBinding.setActivity(this);
        adapter = new SampleAdapter(mData);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        layoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);
        if(layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager)layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(SampleAdapter.TYPE_FOOTER == adapter.getItemViewType(position)) {
                        return GRID_SPAN_COUNT;
                    } else {
                        return 1;
                    }
                }
            });
        }
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if(dy >= 0 && !mBinding.swipeRefreshLayout.isRefreshing() && !isLoading && adapter.getItemCount()>0 && adapter.getRealItemCount() == adapter.getItemCount() && !adapter.isHasFooter()) {
                int lastPosition = -1;
                if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                }
                if(dy == 0) {
                    if (lastPosition >= 0 && (lastPosition + 1 == adapter.getItemCount())) {
                        Log.d(TAG, "onScrolled dy = " + dy);
                        adapter.setHasFooter(true);
                        loadData(mData.size());
                    }
                }
                if(dy > 0 && !isLoading && adapter.getRealItemCount() > 0) {
                    if (lastPosition >= 0 && (lastPosition + 1 == adapter.getItemCount())) {
                        Log.d(TAG, "onScrolled dy = " + dy);
                        adapter.setHasFooter(true);
                        loadData(mData.size());
                    }
                }
            }
        });
        mBinding.swipeRefreshLayout.setColorSchemeResources(SampleConstant.SWIPE_REFRESHING_COLORS);
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isLoading) {
                    return;
                }
                loadData(0);
            }
        });
        mBinding.swipeRefreshLayout.setRefreshing(true);
        loadData(0);
    }

    private void loadData(final int offset) {
        isLoading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int indexTo = offset ==0 ? ROWS_LIMIT : mData.size()+ROWS_LIMIT;
                    if(indexTo > SampleConstant.TESTING_ARRAY.length) indexTo = SampleConstant.TESTING_ARRAY.length;
                    final String[] result = Arrays.copyOfRange(SampleConstant.TESTING_ARRAY, offset, indexTo);
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setViewModel(offset, Arrays.asList(result));
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void setViewModel(final int offset, List<String> list) {
        if (offset == 0) {
            mData.clear();
        }
        if(offset == 0 && list.isEmpty()) {
            Toast.makeText(this, "There is nothing.", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        } else if(offset > 0 && list.isEmpty()) {
            Toast.makeText(this, "No more data.", Toast.LENGTH_SHORT).show();
//            adapter.setHasFooter(false);
            adapter.notifyItemRemoved(adapter.getItemCount());
        } else {
            for(String str: list) {
                ItemVM itemVM = new ItemVM(str);
                mData.add(itemVM);
            }
//            adapter.setHasFooter(false);
            adapter.notifyDataSetChanged();
        }
        mBinding.swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }
}
