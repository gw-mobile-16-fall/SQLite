package com.course.sqlitedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler handler;
    private StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = DatabaseHandler.getInstance(this);
        setContentView(R.layout.activity_main);
        //get the initial data from database
        displayInformation();
    }

    //a function that is used to add student
    public void submit(View view) {
        //get the component EditText
        EditText inputName = (EditText) findViewById(R.id.input_name);
        EditText inputAge = (EditText) findViewById(R.id.input_age);
        //build a model "student" and inject the values into it
        Student student = new Student(inputName.getText().toString(), Integer.parseInt( inputAge.getText().toString() ));
        //insert the student information into the database
        handler.insertStudent(student);
        //clear the EditText
        inputName.setText("");
        inputAge.setText("");
        //focus on the first EditText after clearing
        inputName.requestFocus();
        //update the information displayed on the screen
        displayInformation();
    }

    //update the information displayed on the screen
    public void displayInformation() {
        //get the data from database
        List<Student> students = handler.getStudents();
        //get the component ListView
        ListView listView = (ListView) findViewById(R.id.students_list_view);
        //inject the data into ListView via adapter
        studentAdapter = new StudentAdapter(this, students);
        listView.setAdapter(studentAdapter);
    }

    //custom adaptor
    public class StudentAdapter extends BaseAdapter {
        private Context mContext;
        private List<Student> mDataSource;

        public StudentAdapter(Context mContext, List<Student> mDataSource) {
            this.mContext = mContext;
            this.mDataSource = mDataSource;
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //get the view of a row
            View rowView = View.inflate(mContext, R.layout.list_row, null);

            //get the components in a row
            final TextView textViewId = (TextView) rowView.findViewById(R.id.student_id);
            TextView textViewName = (TextView) rowView.findViewById(R.id.student_name);
            TextView textViewAge = (TextView) rowView.findViewById(R.id.student_age);
            TextView textViewDelete = (TextView) rowView.findViewById(R.id.student_delete);
            Typeface nameTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/RepublicaMinor.otf");
            textViewName.setTypeface(nameTypeFace);

            //inject the data into the components
            textViewId.setText(mDataSource.get(position).getmId()+"");
            textViewName.setText(mDataSource.get(position).getmName());
            textViewAge.setText(mDataSource.get(position).getmAge()+"");

            //set onclick event to the delete icon
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create a confirm dialog
                    new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("DELETE")
                        .setMessage("Are you sure you want to delete\n\""+mDataSource.get(position).getmName()+"\" ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            //when click "yes"
                            public void onClick(DialogInterface dialog, int which) {
                                //get the database instance
                                DatabaseHandler databaseHandler = DatabaseHandler.getInstance(MainActivity.this);
                                //delete the data in the database by _id
                                databaseHandler.deleteStudent(Integer.parseInt(textViewId.getText().toString()));
                                //delete the data in the list by position
                                mDataSource.remove(position);
                                //call the function to refresh the ListView
                                studentAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)  //when click "no"
                        .show();
                }
            });
            return rowView;
        }
    }

}
