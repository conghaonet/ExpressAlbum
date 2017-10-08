package com.app2m.album;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by CongHao on 2017/10/7.
 * E-mail: hao.cong@app2m.com
 */

public class MediaFolderBean implements Serializable {
    private static final String MIX_PATH = "*";
    private String displayName;
    private String path;
    private int contentType;
    private List<MediaBean> medias;
    private Date dateLastModified;

    public MediaFolderBean(@NonNull String displayName, @NonNull List<MediaBean> medias, int contentType) {
        this.displayName = displayName;
        this.path = MIX_PATH;
        this.contentType = contentType;
        this.medias = medias;
    }
    public MediaFolderBean(@Nullable String displayName, @NonNull File fileFolder, @NonNull List<MediaBean> medias, int contentType) {
        this.displayName = displayName;
        this.path = fileFolder.getAbsolutePath();
        this.medias = medias;
        this.contentType = contentType;
        this.dateLastModified = new Date(fileFolder.lastModified());

        if(this.displayName == null) {
            if(this.path.endsWith(File.separator)) {
                this.displayName = this.path.substring(0, this.path.length()-1);
            } else {
                this.displayName = this.path;
            }
            this.displayName = this.displayName.substring(this.displayName.lastIndexOf(File.separator) + 1);
        }
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public List<MediaBean> getMedias() {
        return medias;
    }

    public void setMedias(List<MediaBean> medias) {
        this.medias = medias;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }
    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof MediaFolderBean) {
            MediaFolderBean anotherMediaFolder = (MediaFolderBean)anObject;
            if((path + "_" + contentType).equals(anotherMediaFolder.path + "_" + anotherMediaFolder.contentType)) {
                return true;
            }
        }
        return false;
    }
}
