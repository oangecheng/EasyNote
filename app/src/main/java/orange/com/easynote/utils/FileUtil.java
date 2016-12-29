package orange.com.easynote.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Orange on 2016/12/29.
 */

public class FileUtil {

    public static final int STATE_IMAGE = 0;
    public static final int STATE_OTHER = 1;

    //文件后缀和名称
    public static final String IMAGE_FORMAT = ".jpg";
    public static final String VOICE_FORMAT = ".amr";
    public static final String CACHE_FILE_NAME = "cache_file";

    //文件夹名称
    private static final String BASE_DIR = "easynote";
    private static final String IMAGE_DIR = "image";
    private static final String VOICE_DIR = "voice";
    private static final String CACHE_DIR = "cache";

    public FileUtil() {

    }

    /**
     * 获取缓存文件路径（写权限）.
     *
     * @param name
     * @return 成功获取则返回file, 否则返回null
     */
    public static File getCacheFile(Context context, String name, int state) {
        String fileName = getCacheFileName(name, state);
        return BaseFileUtil.getWritableFile(context, BASE_DIR, CACHE_DIR, fileName);
    }

    private static String getCacheFileName(String name, int state) {
        String fileName = null;
        switch (state) {
            case STATE_IMAGE:
                fileName = name + IMAGE_FORMAT;
                break;
            case STATE_OTHER:
                fileName = name;
                break;
            default:
                break;
        }

        return fileName;
    }

    /**
     * 获取图片文件路径（读权限）
     *
     * @param context
     * @param name
     * @return
     */
    public static File readImageFile(Context context, String name) {
//        String fileName = name + IMAGE_FORMAT;
        return BaseFileUtil.getReadableFile(context, BASE_DIR, IMAGE_DIR, name);
    }

    /**
     * 获取图片文件路径（写权限）
     *
     * @param context
     * @param name
     * @return
     */
    public static File writeImageFile(Context context, String name) {
//        String fileName = name + IMAGE_FORMAT;
        return BaseFileUtil.getWritableFile(context, BASE_DIR, IMAGE_DIR, name);
    }

    /**
     * 获取语音文件路径（读权限）
     *
     * @param context
     * @param name
     * @return
     */
    public static File readVoiceFile(Context context, String name) {
//        String fileName = name + VOICE_FORMAT;
        return BaseFileUtil.getReadableFile(context, BASE_DIR, IMAGE_DIR, name);
    }

    /**
     * 获取语音文件路径（写权限）
     *
     * @param context
     * @param name
     * @return
     */
    public static File writeVoiceFile(Context context, String name) {
//        String fileName = name + VOICE_FORMAT;
        return BaseFileUtil.getWritableFile(context, BASE_DIR, VOICE_DIR, name);
    }


    /**
     * 删除所有文件
     *
     * @param context
     */
    public static void deleteAllFile(Context context) {
        String[] deleteFileDir = new String[]{
                IMAGE_DIR, VOICE_DIR
        };

        for (String dir : deleteFileDir) {
            File file = BaseFileUtil.getWritableDir(context, BASE_DIR, dir);
            BaseFileUtil.deleteRec(file);
        }
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(File oldFile, File newFile) {

        boolean ret = false;

        if (fileExists(oldFile)) {
            try {
                FileInputStream is = new FileInputStream(oldFile);
                byte[] data = new byte[1024];
                FileOutputStream os = new FileOutputStream(newFile);
                while (is.read(data) != -1) {
                    os.write(data);
                }
                is.close();
                os.close();
                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }


    private static boolean fileExists(File file) {
        return file.exists();
    }


}
