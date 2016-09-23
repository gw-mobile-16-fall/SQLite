package com.course.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/19.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME +
                    "( "+ FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NAME+" TEXT, "+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_AGE +" INTEGER)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE "+ FeedReaderContract.FeedEntry.TABLE_NAME +" IF EXISTS";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertStudent(Student student) {
        //use ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, student.getmName());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AGE, student.getmAge());
        //add the values into the database
        this.getWritableDatabase().insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

    public String getStudents() {
        //use cursor to load the data
        String sql = "SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME;
        Cursor cursor = (Cursor) this.getReadableDatabase().rawQuery(sql, null);
        String result = "";
        //get the data in the database row by row via the cursor
        while (cursor.moveToNext()) {
            result += ("\n" + "ID: " + cursor.getInt(0) + "    NAME: " + cursor.getString(1) + "    AGE: " + cursor.getInt(2));
        }
        //if no data in the database, set a default message into the return value
        if (result.length() == 0)
            result = "No data in the database.";
        return result;
    }
}
