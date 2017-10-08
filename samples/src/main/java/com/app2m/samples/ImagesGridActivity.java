package com.app2m.samples;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app2m.album.adapter.MediaAdapter;
import com.app2m.album.bean.MediaBean;
import com.app2m.album.bean.MediaFolderBean;
import com.app2m.album.vm.MediaVM;
import com.app2m.samples.databinding.ActivityImagesGridBinding;

import java.util.ArrayList;
import java.util.List;

public class ImagesGridActivity extends AppCompatActivity {
    private static final String ARG_FOLDER = "arg_folder";
    private ActivityImagesGridBinding mBinding;
    private MediaFolderBean folderBean;
    private MediaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int spanCount = 3;
    private List<MediaVM> mData = new ArrayList<>();

    public static Intent getIntent(Context context, MediaFolderBean folderBean) {
        Intent intent = new Intent(context, ImagesGridActivity.class);
        intent.putExtra(ARG_FOLDER, folderBean);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_images_grid);
        folderBean = (MediaFolderBean)getIntent().getExtras().getSerializable(ARG_FOLDER);
        mBinding.setActivity(this);

        for(MediaBean bean : folderBean.getMedias()) {
            mData.add(new MediaVM(bean));
        }

        mAdapter = new MediaAdapter(mData);
        mLayoutManager = new GridLayoutManager(this ,spanCount);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);
    }
}
