package com.deptchat.livevideocallapp.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.deptchat.livevideocallapp.Adapters.favoratemodule;


public class ConnectCallTB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "call_connect";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "connnecttb";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "image";
    public static final String COLUMN_VIDEO = "video";

    public ConnectCallTB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT," +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_VIDEO + " TEXT);";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String insertData(favoratemodule model) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, model.getName());
        contentValues.put(COLUMN_IMAGE_URL, model.getImage());
        contentValues.put(COLUMN_VIDEO, model.getVideo());

        long result = database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1) {
            return "Failed to insert data";
        } else {
            return "Successfully inserted data";
        }
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return database.rawQuery(query, null);
    }
}
