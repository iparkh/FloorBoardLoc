package ua.iparkh.floorboardloc.db;
//
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//
import java.sql.SQLException;
//
/**
 */
public class DbAdapter {

    // поля базы данных
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SUMMARY = "summary";
    public static final String KEY_DESCRIPTION = "description";
    private static final String DATABASE_TABLE = "todo";
    private Context context;
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    //

    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * создать новый элемент списка дел. если создан успешно - возвращается номер строки rowId
     * иначе -1
     */
    public long createTo(String category, String summary, String description) {
        ContentValues initialValues = createContentValues(category, summary, description);
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * обновить список
     */
    public boolean updateTo(long rowId, String category, String summary, String description) {
        ContentValues updateValues = createContentValues(category, summary,description);

        return database.update(DATABASE_TABLE,
                               updateValues,
                               KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * удаляет элемент списка
     */
    public boolean deleteTo(long rowId) {
        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * возвращает курсор со всеми элементами списка дел
     *
     * @return курсор с результатами всех записей
     */
    public Cursor fetchAllTo() {
        return database.query(DATABASE_TABLE,
                              new String[] { KEY_ROWID, KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION },
                              null, null, null, null, null);
    }

    /**
     * возвращает курсор, спозиционированный на указанной записи
     */
    public Cursor fetchTo(long rowId) throws SQLException {
        Cursor mCursor = database.query(true,
                                        DATABASE_TABLE,
                                        new String[] {KEY_ROWID, KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION },
                                        KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private ContentValues createContentValues(String category, String summary, String description) {

        ContentValues values = new ContentValues();

                      values.put(KEY_CATEGORY, category);
                      values.put(KEY_SUMMARY, summary);
                      values.put(KEY_DESCRIPTION, description);

        return values;
    }

}
