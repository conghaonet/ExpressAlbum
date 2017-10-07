package com.app2m.album;

import java.util.Comparator;

/**
 * Created by CongHao on 2017/10/7.
 * E-mail: hao.cong@app2m.com
 */

public class SortByModifiedDesc implements Comparator<MediaBean> {
    @Override
    public int compare(MediaBean o1, MediaBean o2) {
        if(o1.getDateModified() == null && o2.getDateModified() == null) return 0;
        else if(o1.getDateModified() == null && o2.getDateModified() != null) return 1;
        else if(o1.getDateModified() != null && o2.getDateModified() == null) return -1;
        else if(o1.getDateModified().getTime() < o2.getDateModified().getTime()) return 1;
        else if(o1.getDateModified().getTime() == o2.getDateModified().getTime()) return 0;
        else if(o1.getDateModified().getTime() > o2.getDateModified().getTime()) return -1;
        else return 0;
    }
}
