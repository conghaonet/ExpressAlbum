package com.app2m.samples;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app2m.samples.databinding.SampleActivityMainBinding;
import com.app2m.samples.recyclerview.RecyclerViewExtActivity;

public class SampleMainActivity extends AppCompatActivity {
    private SampleActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.sample_activity_main);
        mBinding.setActivity(this);
    }

    public void onClickRecyclerViewExt(View view) {
        startActivity(new Intent(this, RecyclerViewExtActivity.class));
    }
    public void onClickSampleAlbum(View view) {
        startActivity(new Intent(this, SampleAlbumActivity.class));
    }
}
