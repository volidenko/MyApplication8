package com.example.myapplication2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "webstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "Lists"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DESCRIPTION = "description";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Lists (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT not null, "
                + COLUMN_DATE + " INTEGER not null ,"
                + COLUMN_DESCRIPTION + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME + ", " + COLUMN_DATE  + ", " + COLUMN_DESCRIPTION  + ")" +
                "VALUES ('List1', 1449462120, 'Lorem ipsum dolor sit amet');");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME + ", " + COLUMN_DATE  +  ")" +
                "VALUES ('List2', 1449472233);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME + ", " + COLUMN_DATE  + ")" +
                "VALUES ('List3', 1449483585);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}