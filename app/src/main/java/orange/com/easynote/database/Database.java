package orange.com.easynote.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Orange on 2016/12/22.
 */

public abstract class Database {

    protected SQLiteOpenHelper databaseHelper;
    protected final Context context;

    public Database(Context context, SQLiteOpenHelper databaseHelper) {
        this.context = context;
        this.databaseHelper = databaseHelper;
    }
}
