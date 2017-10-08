package com.app2m.album;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.app2m.album.bean.MediaBean;

import java.math.BigDecimal;

/**
 * Created by CongHao on 2017/10/9.
 * E-mail: hao.cong@app2m.com
 */

public class BindingAdapterUtil {
    @BindingAdapter({"mediaBean"})
    public static void loadAlbumThumbnail(ImageView imageView, MediaBean mediaBean) {
        Bitmap bitmap = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        if(!TextUtils.isEmpty(mediaBean.getThumbnailData())) {
            opt.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(mediaBean.getThumbnailData(), opt);
        } else {
            if(mediaBean.getContentType() == MediaBean.CONTENT_TYPE_IMAGE) {
                opt.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mediaBean.getData(), opt);
                if(opt.outWidth > 0 && opt.outHeight > 0) {
                    float intSampleSize = (float) opt.outWidth / 500f;
                    opt.inSampleSize = new BigDecimal(intSampleSize).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
                    opt.inJustDecodeBounds = false;
                    Bitmap tempBitmap = BitmapFactory.decodeFile(mediaBean.getData(), opt);
                    bitmap = ThumbnailUtils.extractThumbnail(tempBitmap, 500, 500);
                    if(bitmap.getWidth() != tempBitmap.getWidth() || bitmap.getHeight() != tempBitmap.getHeight()) {
                        tempBitmap.recycle();
                    }
                }
            } else if(mediaBean.getContentType() == MediaBean.CONTENT_TYPE_VIDEO) {
                bitmap = ThumbnailUtils.createVideoThumbnail(mediaBean.getData(), MediaStore.Images.Thumbnails.MINI_KIND);
            }
        }
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
