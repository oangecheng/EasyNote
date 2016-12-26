package orange.com.easynote.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.TreeMap;

/**
 * Created by Orange on 2016/12/25.
 */

public class SharedPreferenceUtil {

    //第一次使用App的标记
    public static boolean setFirstStartMark(Context context, boolean mark){
        SharedPreferences share = context.getSharedPreferences(AppMarkTable.TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putBoolean(AppMarkTable.KEY_FIRST_START, mark);
        return editor.commit();
    }

    public static boolean getFirstStartMark(Context context){
        SharedPreferences share = context.getSharedPreferences(AppMarkTable.TABLE_NAME, Context.MODE_PRIVATE);
        return share.getBoolean(AppMarkTable.KEY_FIRST_START, true);
    }


    //设置默认的分类
    public static boolean setCategory(Context context, String category) {
        SharedPreferences share = context.getSharedPreferences(AppMarkTable.TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(AppMarkTable.KEY_CATEGORY, category);
        return editor.commit();
    }

    public static String getCategory(Context context) {
        SharedPreferences share = context.getSharedPreferences(AppMarkTable.TABLE_NAME, Context.MODE_PRIVATE);
        return share.getString(AppMarkTable.KEY_CATEGORY, "其他分类");
    }

    private static final class AppMarkTable {
        private static final String TABLE_NAME = "app_mark_table_name";
        private static final String KEY_FIRST_START = "app_mark_first_start";
        private static final String KEY_CATEGORY = "app_mark_category";
    }

}
