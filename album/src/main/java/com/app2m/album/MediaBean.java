package com.app2m.album;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by CongHao on 2017/10/6.
 * E-mail: hao.cong@app2m.com
 */

public class MediaBean implements Serializable {
    private int id;
    private String data;
    private String thumbnailData;
    private String displayName;
    /**
     * The size of the file in bytes
     */
    private int size;
    private String mimeType;
    private Date dateModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getThumbnailData() {
        return thumbnailData;
    }

    public void setThumbnailData(String thumbnailData) {
        this.thumbnailData = thumbnailData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof MediaBean) {
            if(this.id == ((MediaBean) anObject).getId()) {
                return true;
            }
        }
        return false;
    }
}
