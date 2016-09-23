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
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table students( id integer primary key autoincrement, name txt, age integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table students if exists");
        onCreate(db);
    }

    public void insertStudent(Student student) {
        //use ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put("name", student.getmName());
        values.put("age", student.getmAge());
        //add the values into the database
        this.getWritableDatabase().insert("students", null, values);
    }

    public String getStudents() {
        //use cursor to load the data
        Cursor cursor = (Cursor) this.getReadableDatabase().rawQuery("select * from students", null);
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
