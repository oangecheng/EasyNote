package orange.com.easynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Orange on 2016/12/22.
 */

public class NoteTable extends Database {

    //创建表NoteTable
    public static final String CREATE_TABLE = "CREATE TABLE if not exists "
            + NoteEntry.TABLE_NAME + "("
            + NoteEntry._ID + " integer primary key autoincrement, "
            + NoteEntry.NOTE_TITLE + " text,"
            + NoteEntry.NOTE_CONTENT + " text,"
            + NoteEntry.NOTE_IMAGE + " text,"
            + NoteEntry.NOTE_VOICE + " text,"
            + NoteEntry.NOTE_CATEGORY + " text,"
            + NoteEntry.NOTE_COLLECT + " integer not null );";

    public NoteTable(Context context, SQLiteOpenHelper databaseHelper) {
        super(context, databaseHelper);
    }



    public static class NoteEntry implements BaseColumns {
        //表名
        public static final String TABLE_NAME = "note_table";
        //备忘录标题
        public static final String NOTE_TITLE = "note_title";
        //备忘录内容
        public static final String NOTE_CONTENT = "note_content";
        //备忘录图片
        public static final String NOTE_IMAGE = "note_image";
        //备忘录语音
        public static final String NOTE_VOICE = "note_voice";
        //备忘录分类
        public static final String NOTE_CATEGORY = "note_category";
        //有未被收藏的备忘录
        public static final String NOTE_COLLECT = "note_collect";


    }

}
