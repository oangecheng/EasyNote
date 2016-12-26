package orange.com.easynote.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.R;
import orange.com.easynote.enity.AlbumPhoto;

/**
 * Created by Orange on 2016/12/25.
 */

public class CommonFunction {

    /**
     * 根据image的路径将其显示在imageView当中
     *
     * @param context
     * @param iv
     * @param path
     */
    public static void showImage(Context context, ImageView iv, String path) {
        iv.setImageResource(R.mipmap.ic_launcher);
        ImageLoader.getInstance().loadImage(path, iv);
    }

    /**
     * 获取系统相册的图片
     *
     * @param context
     * @return
     */
    public static List<AlbumPhoto> initAlbum(Context context) {

        List<AlbumPhoto> list = new ArrayList<>();
        //获取大图的游标
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //大图URI
                new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION},  //要获取的字段
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},  //查找jpeg和png两钟格式的图片
                MediaStore.Images.Media.DATE_MODIFIED + " DESC");   //根据时间降序排列

        if (cursor.moveToFirst()) {
            do {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                File file = new File(path);

                if (file.exists()) {
//                    String parentName = file.getParentFile().getParentFile().getParentFile().getName();

                    int degree = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if (degree != 0) {
                        degree = degree + 180;
                    }

                    AlbumPhoto photo = new AlbumPhoto(path, 360 - degree);
                    list.add(photo);
                }
            } while (cursor.moveToNext());

        }
        cursor.close();
        return list;
    }

}
