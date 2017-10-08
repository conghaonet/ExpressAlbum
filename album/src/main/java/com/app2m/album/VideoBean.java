package com.app2m.album;

import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created by CongHao on 2017/10/6.
 * E-mail: hao.cong@app2m.com
 */

public class VideoBean extends MediaBean implements Serializable {
    private long duration;
    private String resolution;

    public VideoBean() {
        super(MediaBean.CONTENT_TYPE_VIDEO);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

}
