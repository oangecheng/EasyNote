package orange.com.easynote.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        iv.setImageResource(R.mipmap.default_img);
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

                if (!file.getAbsolutePath().contains("easynote")) {
                    if (file.exists()) {
//                    String parentName = file.getParentFile().getParentFile().getParentFile().getName();

                        int degree = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        if (degree != 0) {
                            degree = degree + 180;
                        }

                        AlbumPhoto photo = new AlbumPhoto(path, 360 - degree);
                        list.add(photo);
                    }
                }
            } while (cursor.moveToNext());

        }
        cursor.close();
        return list;
    }

    public static String showTime(int time) {
        int hour, minute, second;

        String hh, mm, ss;

        hour = time / 3600;
        minute = (time - hour * 3600) / 60;
        second = time - hour * 3600 - 60 * minute;

        if (hour < 10) {
            hh = "0" + hour;
        } else {
            hh = hour + "";
        }

        if (minute < 10) {
            mm = "0" + minute;
        } else {
            mm = minute + "";
        }

        if (second < 10) {
            ss = "0" + second;
        } else {
            ss = second + "";
        }
        String myTime = "";
        if (hour>0){
            myTime = hh + ":" + mm + ":" + ss;
        }else {
            myTime = mm + ":" + ss;
        }
        return myTime;
    }

    public void cachedThreadPool(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0;i<5;i++){
            System.out.println("***************a"+i+"*********");
        }
        executorService.shutdown();
    }

    private class Run implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"线程被调用了");
        }
    }

}
