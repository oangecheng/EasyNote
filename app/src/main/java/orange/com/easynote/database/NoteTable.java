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


    /**
     * 删除note
     *
     * @param id
     * @return
     */
    public int deleteNote(long id) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        return database.delete(NoteEntry.TABLE_NAME, NoteEntry._ID + "=? ", new String[]{id + ""});
    }


    /**
     * 更新note的collect状态
     *
     * @param id
     * @param collect
     * @return
     * @throws Exception
     */
    public boolean updateNoteCollect(long id, int collect) throws Exception {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NoteEntry.NOTE_COLLECT, collect);
        return (database.update(NoteEntry.TABLE_NAME, cv, NoteEntry._ID + "=?", new String[]{id + ""}) != -1);
    }


    public boolean updateNote(long id, String title, String content, String image, String voice, String category) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NoteEntry.NOTE_TITLE, title);
        cv.put(NoteEntry.NOTE_CONTENT, content);
        cv.put(NoteEntry.NOTE_IMAGE, image);
        cv.put(NoteEntry.NOTE_VOICE, voice);
        cv.put(NoteEntry.NOTE_CATEGORY, category);

        return (database.update(NoteEntry.TABLE_NAME, cv, NoteEntry._ID + "=?", new String[]{String.valueOf(id)}) != -1);
    }


    /**
     * 根据不同的模式获取note的数据
     *
     * @param mode
     * @param param 查询的参数
     * @return
     */
    public List<NoteInfo> getNoteList(int mode, String param) {
        List<NoteInfo> list = new ArrayList<>();
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        try {
            Cursor cursor = null;
            switch (mode) {

                case 0:
                    cursor = database.query(
                            //查哪个表
                            NoteEntry.TABLE_NAME,
                            //查哪些些东西
                            null,
                            //根据什么查
                            null, null, null, null, NoteEntry._ID + " desc");
                    break;

                case 1:
                    cursor = database.query(
                            //查哪个表
                            NoteEntry.TABLE_NAME,
                            //查哪些些东西
                            null,
                            //根据什么查
                            NoteEntry.NOTE_CATEGORY + "=?", new String[]{param},
                            null, null, null);
                    break;

                case 2:
                    cursor = database.query(
                            NoteEntry.TABLE_NAME,
                            null,
                            NoteEntry.NOTE_COLLECT + "=?", new String[]{String.valueOf(1)},
                            null, null,
                            NoteEntry._ID + " desc");
                    break;

                case 3:
                    cursor = database.query(
                            NoteEntry.TABLE_NAME,
                            null,
                            NoteEntry._ID + "=?", new String[]{param},
                            null, null, null);
                    break;

                default:
                    break;

            }

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


    /**
     * 查询每个类别note的数量
     *
     * @param mode     0代表查询收藏的，1代表根据类别查询
     * @param category
     * @return
     */
    public int getCountByCategory(int mode, String category) {

        int count = 0;
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        try {
            Cursor cursor = null;
            switch (mode) {
                case 0:
                    cursor = database.query(
                            NoteEntry.TABLE_NAME,
                            new String[]{NoteEntry._ID},
                            null, null, null, null, null);
                    break;
                case 1:
                    cursor = database.query(
                            //查哪个表
                            NoteEntry.TABLE_NAME,
                            //查哪些些东西
                            new String[]{NoteEntry.NOTE_CATEGORY},
                            //根据什么查
                            NoteEntry.NOTE_CATEGORY + "=?", new String[]{category},
                            null, null, null);
                    break;
                case 2:
                    cursor = database.query(
                            NoteEntry.TABLE_NAME,
                            new String[]{NoteEntry.NOTE_COLLECT},
                            NoteEntry.NOTE_COLLECT + "=?", new String[]{String.valueOf(1)},
                            null, null, null);
                    break;
                default:
                    break;
            }
            count = cursor.getCount();
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
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
