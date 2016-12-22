package orange.com.easynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Orange on 2016/12/22.
 */

public class CategoryTable extends Database {

    //创建表NoteTable
    public static final String CREATE_TABLE = "CREATE TABLE if not exists "
            + CategoryEntry.TABLE_NAME + "("
            + CategoryEntry._ID + " integer primary key autoincrement, "
            + CategoryEntry.NOTE_TITLE + " text );";

    public CategoryTable(Context context, SQLiteOpenHelper databaseHelper) {
        super(context, databaseHelper);
    }

    public static class CategoryEntry implements BaseColumns {
        //表名
        public static final String TABLE_NAME = "category_table";
        //类别标题
        public static final String NOTE_TITLE = "category_title";
    }
}
