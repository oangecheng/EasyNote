package orange.com.easynote.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Orange on 2016/12/29.
 */

public class BaseFileUtil {

    private BaseFileUtil() {

    }

    /**
     * 判断是否是文件夹路径.
     *
     * @param file 文件夹路径
     * @return 不正确则返回false
     */
    private static boolean isDirectory(File file) {
        return (isExistDirectory(file) || file.mkdirs());
    }

    /**
     * 判断是否是已存在的文件夹路径.
     *
     * @param file 文件夹路径
     * @return 不正确则返回false
     */
    private static boolean isExistDirectory(File file) {
        boolean ret = false;
        if ((file != null && file.exists())) {
            if (file.isDirectory()) {
                ret = true;
            } else {
                if (file.isFile()) {
                    System.out.println("a file with the same name exists");
                }
            }
        }
        return  ret;
    }

    /**
     * SD卡存储是否可写.
     *
     * @return 可写则返回true.
     */
    private static boolean isExternalStorage() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 获取内部存储路径.
     *
     * @return 目录File对象.
     */
    private static File getPrivateDir(Context context, String baseDir, String fileDir) {
        String[] str = baseDir.split("\\/");
        File file = context.getDir(str[0], Context.MODE_PRIVATE);
        for (int i = 1; i < str.length; i++) {
            file = new File(file, str[i]);
        }
        file = new File(file, fileDir);
        return file;
    }

    /**
     * 搜索可写的SD卡的根目录
     *
     * @param fileDir File名称
     * @return 目录File对象.
     */
    private static File getSdDir(String baseDir, String fileDir) {
        File file = new File("/");
        File[] allFile = file.listFiles();
        int i = 0;
        for (; i < allFile.length; i++) {
            if (allFile[i].getName().contains("sdcard")
                    && allFile[i].canWrite()) {
                break;
            }
        }
        if (i < allFile.length) {
            file = new File(allFile[i], baseDir);
            file = new File(file, fileDir);
        } else {
            file = null;
        }
        return file;
    }

    /**
     * 获取拥有写权限的文件夹.
     *
     * @return 目录File对象.
     */
    public static File getWritableDir(Context context, String baseDir, String dirName) {
        File file;
        if (isExternalStorage()) {
            file = new File(Environment.getExternalStorageDirectory(), baseDir);
            file = new File(file, dirName);
        } else {
            file = getSdDir(baseDir, dirName);
            if (file == null) {
                file = getPrivateDir(context, baseDir, dirName);
            }
        }
        return file;
    }

    /**
     * 获取拥有写权限的文件.
     *
     * @return 目录File对象.
     */
    public static File getWritableFile(Context context, String baseDir, String dirName, String fileName) {
        File file = getWritableDir(context, baseDir, dirName);
        if (isDirectory(file)) {
            return new File(file, fileName);
        } else {
            return null;
        }
    }

    /**
     * 获取拥有读权限的文件.
     *
     * @return 目录File对象.
     */
    public static File getReadableFile(Context context, String baseDir, String dirName, String fileName) {
        File file;
        if (isExternalStorage()) {
            file = new File(Environment.getExternalStorageDirectory(), baseDir);
            file = new File(file, dirName);
            file = new File(file, fileName);
            if ((file.exists()) && (file.isFile())) {
                return file;
            }
        } else {
            file = getSdDir(baseDir, dirName);
            file = new File(file, fileName);
            if ((file.exists()) && (file.isFile())) {
                return file;
            }

            file = getPrivateDir(context, baseDir, dirName);
            file = new File(file, fileName);
            if ((file.exists()) && (file.isFile())) {
                return file;
            }
        }

        return null;
    }

    /**
     * 递归删除目录或删除文件.
     *
     * @param file 目录或文件.
     */
    public static void deleteRec(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            System.out.println("delete file： " + file.getName());
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if ((childFiles == null) || (childFiles.length == 0)) {
                file.delete();
                return;
            } else {
                for (File childFile : childFiles) {
                    deleteRec(childFile);
                }
                file.delete();
            }
        }
    }



}
