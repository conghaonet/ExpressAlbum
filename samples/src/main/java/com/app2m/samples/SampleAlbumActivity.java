package com.app2m.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app2m.album.AlbumUtil;
import com.app2m.album.bean.MediaFolderBean;

import java.util.List;

public class SampleAlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_album);
    }

    public void onClickAlbumUtilGetAllMediasByFolder(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlbumUtil.getAllMediasByFolder(getApplicationContext());
            }
        }).start();
    }
    public void onClickAlbumUtiGetAllImages(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlbumUtil.getAllImages(getApplicationContext());
            }
        }).start();
    }
    public void onClickAlbumUtiGetAllVideos(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlbumUtil.getAllVideos(getApplicationContext());
            }
        }).start();
    }

    public void onClickImagesGridActivity(View view) {
        List<MediaFolderBean> list = AlbumUtil.getAllMediasByFolder(this.getApplicationContext());
        if (!list.isEmpty()) {
            startActivity(ImagesGridActivity.getIntent(this, list.get(1)));
        } else {
            Toast.makeText(this, "nothing!", Toast.LENGTH_SHORT).show();
        }
    }

}
