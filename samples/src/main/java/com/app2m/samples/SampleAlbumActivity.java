package com.app2m.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app2m.album.AlbumUtil;

public class SampleAlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_album);
    }

    public void onClickAlbumUtilGetAllFolder(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlbumUtil.getAllFolders(getApplicationContext());
            }
        }).start();
    }
}
