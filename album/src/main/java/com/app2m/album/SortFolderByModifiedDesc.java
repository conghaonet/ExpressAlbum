package com.app2m.album;

import java.util.Comparator;

/**
 * Created by CongHao on 2017/10/8.
 * E-mail: hao.cong@app2m.com
 */

public class SortFolderByModifiedDesc implements Comparator<MediaFolderBean> {
    @Override
    public int compare(MediaFolderBean o1, MediaFolderBean o2) {
        if(o1.getDateLastModified() == null && o2.getDateLastModified() == null) return 0;
        else if(o1.getDateLastModified() == null && o2.getDateLastModified() != null) return 1;
        else if(o1.getDateLastModified() != null && o2.getDateLastModified() == null) return -1;
        else if(o1.getDateLastModified().getTime() < o2.getDateLastModified().getTime()) return 1;
        else if(o1.getDateLastModified().getTime() == o2.getDateLastModified().getTime()) return 0;
        else if(o1.getDateLastModified().getTime() > o2.getDateLastModified().getTime()) return -1;
        else return 0;
    }
}
