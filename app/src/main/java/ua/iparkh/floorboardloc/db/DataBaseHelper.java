package ua.iparkh.floorboardloc.db;
//
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//
import android.util.Log;
//
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
//

/**
 */
public class DataBaseHelper extends SQLiteOpenHelper {
//
    private static String DB_PATH = "/data/data/ua.iparkh.floorboardloc/databases/";
    private static String DB_NAME = "dbName";
    private SQLiteDatabase dataBase;
    private final Context context;

    private static final String DATABASE_CREATE =
              "create table todo (_id integer primary key autoincrement, "
                               + "category text not null,"
                               + " summary text not null,"
                               + " description text not null);";



    //
  public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }
//
    @Override
    public void onCreate(SQLiteDatabase db) {
        dataBase.execSQL(DATABASE_CREATE);
    }
//
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        dataBase.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(dataBase);
    }
//
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //ничего не делать - база уже есть
        }else{

            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }
//
    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }
//
    private void copyDataBase() throws IOException{

        //Открываем локальную БД как входящий поток
        InputStream myInput = context.getAssets().open(DB_NAME);

        //Путь ко вновь созданной БД
        String outFileName = DB_PATH + DB_NAME;

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
//
public void openDataBase() throws SQLException {

    //открываем БД
    String myPath = DB_PATH + DB_NAME;
    dataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

}
//
    @Override
    public synchronized void close() {

        if(dataBase != null)
            dataBase.close();

        super.close();

    }
//
}
