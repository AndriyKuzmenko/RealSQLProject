package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddGrades extends AppCompatActivity
{
    SQLiteDatabase db;
    HelperDB hlp;
    EditText student,address,quarter,subject,grade;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        student=(EditText)findViewById(R.id.student);
        quarter=(EditText)findViewById(R.id.quarter);
        subject=(EditText)findViewById(R.id.subject);
        grade=(EditText)findViewById(R.id.grade);
    }

    public void save(View view)
    {
        ContentValues cv = new ContentValues();

        cv.put(Grades.STUDENT, student.getText().toString());
        cv.put(Grades.QUARTER, Integer.parseInt(quarter.getText().toString()));
        cv.put(Grades.SUBJECT, subject.getText().toString());
        cv.put(Grades.GRADE, Integer.parseInt(grade.getText().toString()));

        db = hlp.getWritableDatabase();

        db.insert(Users.TABLE_USERS, null, cv);

        db.close();
    }
}