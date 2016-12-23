package orange.com.easynote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import orange.com.easynote.enity.NoteInfo;

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
            + NoteEntry.NOTE_TIME + " text,"
            + NoteEntry.NOTE_IMAGE + " text,"
            + NoteEntry.NOTE_VOICE + " text,"
            + NoteEntry.NOTE_CATEGORY + " text,"
            + NoteEntry.NOTE_COLLECT + " integer not null );";

    public NoteTable(Context context, SQLiteOpenHelper databaseHelper) {
        super(context, databaseHelper);
    }

    /**
     * 新建note
     *
     * @param title
     * @param content
     * @param image
     * @param voice
     * @param category
     * @param collect
     * @return
     */
    public boolean insertNote(String title, String content, String time, String image, String voice, String category, int collect) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NoteEntry.NOTE_TITLE, title);
        cv.put(NoteEntry.NOTE_CONTENT, content);
        cv.put(NoteEntry.NOTE_TIME, time);
        cv.put(NoteEntry.NOTE_IMAGE, image);
        cv.put(NoteEntry.NOTE_VOICE, voice);
        cv.put(NoteEntry.NOTE_CATEGORY, category);
        cv.put(NoteEntry.NOTE_COLLECT, collect);
        return (database.insert(NoteEntry.TABLE_NAME, null, cv) != -1);
    }


    public List<NoteInfo> getNoteList(String category) {
        List<NoteInfo> list = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        try {
            Cursor cursor = database.query(
                    //查哪个表
                    NoteEntry.TABLE_NAME,
                    //查哪些些东西
                    new String[]{NoteEntry._ID, NoteEntry.NOTE_TITLE, NoteEntry.NOTE_CONTENT, NoteEntry.NOTE_TIME, NoteEntry.NOTE_IMAGE, NoteEntry.NOTE_VOICE, NoteEntry.NOTE_CATEGORY, NoteEntry.NOTE_COLLECT},
                    //根据什么查
                    NoteEntry.NOTE_CATEGORY + "=?", new String[]{category},
                    null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(new NoteInfo(
                            cursor.getLong(cursor.getColumnIndex(NoteEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE_TITLE)),
                            cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE_CONTENT)),
                            cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE_TIME)),
                            cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE_VOICE)),
                            cursor.getString(cursor.getColumnIndex(NoteEntry.NOTE_CATEGORY)),
                            cursor.getInt(cursor.getColumnIndex(NoteEntry.NOTE_COLLECT))));
                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static class NoteEntry implements BaseColumns {
        //表名
        public static final String TABLE_NAME = "note_table";
        //备忘录标题
        public static final String NOTE_TITLE = "note_title";
        //备忘录内容
        public static final String NOTE_CONTENT = "note_content";
        //时间
        public static final String NOTE_TIME = "note_time";
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
