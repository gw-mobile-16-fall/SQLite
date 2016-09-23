package com.course.sqlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            context	(Context): to use to open or create the database
            name	(String): of the database file, or null for an in-memory database
            factory	(SQLiteDatabase.CursorFactory): to use for creating cursor objects, or null for the default
            version	(int): number of the database (starting at 1);
        */
        handler = new DatabaseHandler(this, "test", null, 1);
        setContentView(R.layout.activity_main);
        //get the initial data from database
        update();
    }

    //a function that is used to add student
    public void addStudent(View view) {
        //get the component of the textfields
        EditText inputName = (EditText) findViewById(R.id.input_name);
        EditText inputAge = (EditText) findViewById(R.id.input_age);
        //build a model "student" and inject the value into it
        Student student = new Student(inputName.getText().toString(), Integer.parseInt( inputAge.getText().toString() ));
        //insert the student message into the database
        handler.insertStudent(student);
        //update the information displayed on the screen
        update();
    }

    //update the information displayed on the screen
    public void update() {
        //get the component of the TextView
        TextView textView = (TextView) findViewById(R.id.result);
        //get the data from database
        String result = handler.getStudents();
        //update the information displayed on the screen
        textView.setText(result);
    }
}
