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
        handler = new DatabaseHandler(this, "test", null, 1);
        setContentView(R.layout.activity_main);
    }

    public void addStudent(View view) {
        EditText inputName = (EditText) findViewById(R.id.input_name);
        EditText inputAge = (EditText) findViewById(R.id.input_age);
        Student student = new Student(inputName.getText().toString(), Integer.parseInt( inputAge.getText().toString() ));
        handler.insertStudent(student);
        update();
    }

    public void update() {
        TextView textView = (TextView) findViewById(R.id.result);
        handler.getStudents(textView);
    }
}
