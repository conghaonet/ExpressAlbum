package com.app2m.album;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.util.Date;

/**
 * Created by CongHao on 2017/10/6.
 * E-mail: hao.cong@app2m.com
 */

public class AlbumUtil {
    public static void getAllFolders(Context context) {
        Uri externalContentUri = MediaStore.Files.getContentUri("external");
        String[] projections = {MediaStore.Files.FileColumns._ID,
                                MediaStore.Files.FileColumns.DATA,
                                MediaStore.Files.FileColumns.SIZE,
                                MediaStore.Files.FileColumns.DISPLAY_NAME,
                                MediaStore.Files.FileColumns.MIME_TYPE,
                                MediaStore.Files.FileColumns.DATE_MODIFIED
                               };
        Cursor cursor = context.getContentResolver().query(externalContentUri, projections,
                MediaStore.Images.Media.MIME_TYPE + " like ? or " + MediaStore.Images.Media.MIME_TYPE + " like ? or " + MediaStore.Images.Media.MIME_TYPE +" like ?",
                new String[]{"image/%", "video/%", "audio/%"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                // 获取图片的路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE))/1024;
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
                String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));
                String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));

                Log.d("AlbumUtil.getAllFolders", "path: "+path);
            }
            cursor.close();
        }
    }

    public static void getAllMedias(Context context) {

    }
    public static SparseArray<ImageBean> getAllImages(Context context) {
        SparseArray<ImageBean> saImages = null;
        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATE_MODIFIED
        };
        Cursor cursor = context.getContentResolver().query(externalContentUri, projections, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if(cursor!=null) {
            saImages = new SparseArray<>(cursor.getCount());
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                String dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                ImageBean bean = new ImageBean();
                bean.setId(_id);
                bean.setData(data);
                bean.setSize(size);
                bean.setDisplayName(displayName);
                bean.setMimeType(mimeType);
                bean.setLastModified(getModifiedDate(dateModified));
                saImages.put(_id, bean);
            }
            cursor.close();
            setMediasThumbnail(context, saImages);

            //TODO: for debug
            for(int i=0; i<saImages.size(); i++) {
                ImageBean bean = saImages.valueAt(i);
                if(!TextUtils.isEmpty(bean.getThumbnailData())) {
                    Log.d("getAllImages", "data = " + bean.getData());
                    Log.d("getAllImages", "thumbnail = " + bean.getThumbnailData());
                }
            }

        }
        return saImages != null ? saImages : new SparseArray<ImageBean>();
    }

    public static SparseArray<VideoBean> getAllVideos(Context context) {
        SparseArray<VideoBean> saVideos = null;
        Uri externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.DATE_MODIFIED
        };
        Cursor cursor = context.getContentResolver().query(externalContentUri, projections, null, null, MediaStore.Video.Media.DATE_MODIFIED + " desc");
        if(cursor!=null) {
            saVideos = new SparseArray<>(cursor.getCount());
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                String dateModified = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String resolution = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.RESOLUTION));

                VideoBean bean = new VideoBean();
                bean.setId(_id);
                bean.setData(data);
                bean.setSize(size);
                bean.setDisplayName(displayName);
                bean.setMimeType(mimeType);
                bean.setDuration(duration);
                bean.setResolution(resolution);
                bean.setLastModified(getModifiedDate(dateModified));
                saVideos.put(_id, bean);
            }
            cursor.close();
            setMediasThumbnail(context, saVideos);

            //TODO: for debug
            for(int i=0; i<saVideos.size(); i++) {
                VideoBean bean = saVideos.valueAt(i);
                if(!TextUtils.isEmpty(bean.getThumbnailData())) {
                    Log.d("getAllVideos", "data = " + bean.getData());
                    Log.d("getAllVideos", "thumbnail = " + bean.getThumbnailData());
                }
            }
        }
        return saVideos != null ? saVideos : new SparseArray<VideoBean>();
    }

    private static void setMediasThumbnail(Context context, SparseArray<? extends MediaBean> medias) {
        if(medias == null || medias.size() == 0) {
            return;
        }
        Uri externalContentUri;
        String[] projections = new String[2];
        if(medias.valueAt(0).getClass().equals(ImageBean.class)) {
            externalContentUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
            projections[0] = MediaStore.Images.Thumbnails.DATA;
            projections[1] = MediaStore.Images.Thumbnails.IMAGE_ID;
        } else if(medias.valueAt(0).getClass().equals(VideoBean.class)) {
            externalContentUri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
            projections[0] = MediaStore.Video.Thumbnails.DATA;
            projections[1] = MediaStore.Video.Thumbnails.VIDEO_ID;
        } else {
            return;
        }
        Cursor cursor = context.getContentResolver().query(externalContentUri, projections, null, null, null);
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(projections[0]));
                int id = cursor.getInt(cursor.getColumnIndex(projections[1]));
                MediaBean bean = medias.get(id);
                if(bean != null) {
                    bean.setThumbnailData(data);
                }
            }
            cursor.close();
        }
    }

    /**
     *
     * @param modified Units are seconds since 1970.
     * @return
     */
    private static Date getModifiedDate(String modified) {
        if(modified != null) {
            if(modified.length() == 10) { //Units are seconds since 1970.
                return new Date(Long.parseLong(modified) * 1000);
            } else if(modified.length() == 13) {//Units are milliseconds since 1970.
                return new Date(Long.parseLong(modified));
            }
        }
        return null;
    }
}
