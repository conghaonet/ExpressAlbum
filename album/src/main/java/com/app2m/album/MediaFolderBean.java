package com.app2m.album;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CongHao on 2017/10/7.
 * E-mail: hao.cong@app2m.com
 */

public class MediaFolderBean implements Serializable {
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_MIX = "mix";

    private String folderName;
    private String path;
    private String type;
    private List<MediaBean> medias;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MediaBean> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaBean> medias) {
        this.medias = medias;
    }
}
