package orange.com.easynote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.enity.CategoryInfo;
import orange.com.easynote.utils.AppConstant;
import orange.com.easynote.utils.SharedPreferenceUtil;

/**
 * Created by Orange on 2016/12/22.
 */

public class CategoryTable extends Database {

    //创建表NoteTable
    public static final String CREATE_TABLE = "CREATE TABLE if not exists "
            + CategoryEntry.TABLE_NAME + "("
            + CategoryEntry._ID + " integer primary key autoincrement, "
            + CategoryEntry.CATEGORY_TITLE + " text );";

    public CategoryTable(Context context, SQLiteOpenHelper databaseHelper) {
        super(context, databaseHelper);
    }


    //查询分类的列表
    public List<CategoryInfo> getCategoryList() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        List<CategoryInfo> list = new ArrayList<>();
        try {
            Cursor cursor = database.query(CategoryEntry.TABLE_NAME,
                    new String[]{CategoryEntry._ID, CategoryEntry.CATEGORY_TITLE}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(CategoryEntry._ID));
                    String title = cursor.getString(cursor.getColumnIndex(CategoryEntry.CATEGORY_TITLE));
                    int count = DatabaseFactory.getNoteTable(context).getCountByCategory(AppConstant.MODE_1, title);
                    if (!title.equals(SharedPreferenceUtil.getCategory(context))){
                        list.add(new CategoryInfo(title, count, id, false));
                    }else {
                        list.add(new CategoryInfo(title, count, id, true));
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //添加新的分类
    public boolean insertCategory(String category) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CategoryEntry.CATEGORY_TITLE, category);
        return (database.insert(CategoryEntry.TABLE_NAME, null, cv) != -1);
    }

    //判断当前分类是否存在,true为已存在
    public boolean categoryExist(String category) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        try {
            Cursor cursor = database.query(CategoryEntry.TABLE_NAME, new String[]{CategoryEntry.CATEGORY_TITLE}, CategoryEntry.CATEGORY_TITLE + "=?", new String[]{category},
                    null, null, null);
            if (cursor.getCount() != 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static class CategoryEntry implements BaseColumns {
        //表名
        public static final String TABLE_NAME = "category_table";
        //类别标题
        public static final String CATEGORY_TITLE = "category_title";
    }
}
