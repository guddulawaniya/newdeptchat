package com.deptchat.livevideocallapp.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.deptchat.livevideocallapp.Adapters.favoratemodule;

public class chatHalper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "chattb";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "image";
    public static final String COLUMN_VIDEO = "video";

    public chatHalper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER, " +
                COLUMN_NAME + " TEXT," +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_VIDEO + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertdata(favoratemodule model) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, model.getId());
        contentValues.put(COLUMN_NAME, model.getName());
        contentValues.put(COLUMN_IMAGE_URL, model.getImage());
        contentValues.put(COLUMN_VIDEO, model.getVideo());

        long result = database.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        return result != -1;
    }

    public void deleteDataById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Use the DELETE statement with a WHERE clause to specify the ID
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;

        db.execSQL(deleteQuery);
        db.close();
    }

    public Cursor getdata() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        // Don't forget to close the cursor when done
        // cursor.close(); // Uncomment if not using try-with-resources

        return cursor;
    }
}
