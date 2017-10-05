package com.app2m.album;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by CongHao on 2017/10/6.
 * E-mail: hao.cong@app2m.com
 */

public class AlbumUtil {
    public static void getAllFolders(Context context) {
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projectionImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
//        Cursor cursor = context.getContentResolver().query(imageUri, projectionImage,
//                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE +"=?",
//                new String[]{"image/jpeg", "image/png", "image/gif"},
//                MediaStore.Images.Media.DATE_MODIFIED+" desc");
        Cursor cursor = context.getContentResolver().query(imageUri, projectionImage, null, null, MediaStore.Images.Media.DATE_MODIFIED+" desc");
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                // 获取图片的路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                Log.d("AlbumUtil.getAllFolders", "path: "+path);
                Log.d("AlbumUtil.getAllFolders", "displayName: "+displayName);
                Log.d("AlbumUtil.getAllFolders", "size: "+size);
            }
        }
    }
}
