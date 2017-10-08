package com.app2m.album.vm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.app2m.album.bean.MediaBean;

/**
 * Created by CongHao on 2017/10/8.
 * E-mail: hao.cong@app2m.com
 */

public class MediaVM extends BaseObservable {
    private MediaBean mediaBean;
    public MediaVM(MediaBean bean) {
        this.mediaBean = bean;
    }

    @Bindable
    public MediaBean getMediaBean() {
        return mediaBean;
    }

}
