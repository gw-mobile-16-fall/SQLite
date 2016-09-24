package com.course.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    private static DatabaseHandler instance;
    private static final String DATABASE_NAME = "test";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME +
                    "( "+ FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NAME+" TEXT, "+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_AGE +" INTEGER)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE "+ FeedReaderContract.FeedEntry.TABLE_NAME +" IF EXISTS";

    //use SINGLETON pattern, in order to prevent from creating multiple class
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler(context.getApplicationContext());
        }
        return instance;
    }

    //set the constructor to private
    private DatabaseHandler(Context context) {
        /*
            context	(Context): to use to open or create the database
            name	(String): of the database file, or null for an in-memory database
            factory	(SQLiteDatabase.CursorFactory): to use for creating cursor objects, or null for the default
            version	(int): number of the database (starting at 1);
        */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if version updated, drop the original database and create a new one
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

    public List<Student> getStudents() {
        String sql = "SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME;
        //use cursor to load the data
        Cursor cursor = (Cursor) this.getReadableDatabase().rawQuery(sql, null);
        List<Student> students = new ArrayList<Student>();
        //get the data in the database row by row via the cursor
        while (cursor.moveToNext()) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            students.add(student);
        }
        return students;
    }

    public void deleteStudent(int id) {
        String whereClause = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] whereArgs = {id+""};
        //delete the data by id
        this.getWritableDatabase().delete(FeedReaderContract.FeedEntry.TABLE_NAME, whereClause, whereArgs);
    }
}
