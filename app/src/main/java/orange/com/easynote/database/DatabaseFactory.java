package orange.com.easynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 2016/12/22.
 */

public class DatabaseFactory {

    //数据库的版本号
    private static final int DATABASE_VERSION = 1;

    //数据库名称
    private static final String DATABASE_NAME = "Orange.db";

    //用来实现锁定数据库的表
    private static final Object LOCK = new Object();

    //数据库
    private static DatabaseFactory instance;

    //NoteTable表的对象
    private final NoteTable noteTable;

    //CategoryTable表的对象
    private final CategoryTable categoryTable;

    //数据库的辅助工具
    private DatabaseHelper databaseHelper;


    public DatabaseFactory(final Context context) {
        this.databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.noteTable = new NoteTable(context, databaseHelper);
        this.categoryTable = new CategoryTable(context, databaseHelper);
    }


    public static NoteTable getNoteTable(final Context context) {
        return getInstance(context).noteTable;
    }

    public static CategoryTable getCategoryTable(final Context context){
        return getInstance(context).categoryTable;
    }


    public static DatabaseFactory getInstance(final Context context) {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new DatabaseFactory(context.getApplicationContext());
            }
            return instance;
        }
    }

    /**
     * 清空指定的数据表，为private是为了提供给clearAllTable调用
     *
     * @param tableName 需要删除的表的名称
     */
    private void clearTable(String tableName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(tableName, null, null);

            //将数据库中的表自增列清零
            String sql = " update sqlite_sequence SET seq = 0 where name = '" + tableName + "' ;";

            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /*
    清空所有的表，调用的方法为 DatabaseFactory.getInstance(context).clearAllTable();
     */
    public boolean clearAllTable() {
        boolean ret = false;
        List<String> tableList = new ArrayList<>();

        //将需要清空的表加入到tableList
        tableList.add(NoteTable.NoteEntry.TABLE_NAME);
        tableList.add(CategoryTable.CategoryEntry.TABLE_NAME);

        for (int i = 0; i < tableList.size(); i++) {
            try {
                clearTable(tableList.get(i));
                ret = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    //数据库的辅助类，为内部类
    private static final class DatabaseHelper extends SQLiteOpenHelper {
        private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(final SQLiteDatabase db) {
            db.execSQL(NoteTable.CREATE_TABLE);
            db.execSQL(CategoryTable.CREATE_TABLE);
        }

        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

        }
    }

}
