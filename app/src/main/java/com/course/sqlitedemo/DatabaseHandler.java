package com.course.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

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
        ContentValues values = new ContentValues();
        values.put("name", student.getmName());
        values.put("age", student.getmAge());
        this.getWritableDatabase().insert("students", null, values);
    }

    public void getStudents(TextView view) {
        Cursor cursor = (Cursor) this.getReadableDatabase().rawQuery("select * from students", null);
        String result = "";
        while (cursor.moveToNext()) {
            result += ("\n" + "ID: " + cursor.getInt(0) + "    NAME: " + cursor.getString(1) + "    AGE: " + cursor.getInt(2));
        }
        view.setText(result);
    }
}
