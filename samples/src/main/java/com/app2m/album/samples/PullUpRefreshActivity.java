package com.app2m.album.samples;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.app2m.album.samples.databinding.SampleActivityPullUpRefreshBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PullUpRefreshActivity extends AppCompatActivity {
    private static final String TAG = PullUpRefreshActivity.class.getName();
    private SampleActivityPullUpRefreshBinding mBinding;
    private static final int ROWS_LIMIT = 20;
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
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        layoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);
        layoutManager = new StaggeredGridLayoutManager(GRID_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
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
                int lastPosition = -1;
                if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                } else if(layoutManager instanceof StaggeredGridLayoutManager) {
                    //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                    //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    lastPosition = findMax(lastPositions);
                }
                if(dy >= 0 && !isLoading && SampleAdapter.TYPE_FOOTER == adapter.getItemViewType(lastPosition)) {
                    Log.d(TAG, "onScrolled dy = " + dy);
                    loadData(mData.size());
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
    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
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
            adapter.notifyItemRemoved(adapter.getItemCount());
        } else {
            for(String str: list) {
                ItemVM itemVM = new ItemVM(str);
                mData.add(itemVM);
            }
            adapter.notifyDataSetChanged();
        }
        mBinding.swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }
}
