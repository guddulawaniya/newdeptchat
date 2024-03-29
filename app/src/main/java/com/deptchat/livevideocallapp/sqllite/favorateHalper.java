package com.deptchat.livevideocallapp.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.deptchat.livevideocallapp.Adapters.favoratemodule;


public class favorateHalper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorate_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favorate_tb";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "image";
    public static final String COLUMN_VIDEO = "video";

    public favorateHalper(Context context) {
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

        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        onCreate(db);

    }

    public void deleteRowWithCondition(String conditionValue) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause
        String whereClause = "name=?";

        // Specify the value to be used in the WHERE clause
        String[] whereArgs = { conditionValue };

        // Execute the delete query with parameterized values
        db.delete(TABLE_NAME, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }


    public String insertdata(favoratemodule model) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, model.getName());
        contentValues.put(COLUMN_IMAGE_URL, model.getImage());
        contentValues.put(COLUMN_VIDEO, model.getVideo());

        float result = database.insertWithOnConflict(TABLE_NAME, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE);

        if (result == - 1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }


    }

    public Cursor getdata() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select * from "+TABLE_NAME;                               //Sql query to  retrieve  data from the database
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
}
